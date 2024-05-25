package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.StatusPagamentoEnum;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.exception.negocio.PagamentoException;
import br.com.farmadelivery.exception.negocio.PedidoException;
import br.com.farmadelivery.factory.FactoryPedido;
import br.com.farmadelivery.factory.FactoryPedidoEntity;
import br.com.farmadelivery.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Lazy
    @Autowired
    private EntregaService entregaService;

    @Autowired
    private FactoryPedidoEntity factoryPedidoEntity;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private FactoryPedido factoryPedido;

    @Autowired
    private ProdutosPedidosService produtosPedidosService;

    public Optional<PedidoEntity> consulta(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public List<Pedido> consultaPendentesDeValidacao() {
        List<PedidoEntity> entities = pedidoRepository.findAllByStatus(StatusPedidoEnum.VALIDACAO_PENDENTE);
        List<Pedido> pedidos = new ArrayList<>();
        entities.forEach(entity -> {
            Map<ProdutoEntity, List<Long>> produtos = new HashMap<>();
            List<Long> anexoIds = new ArrayList<>();
            List<ProdutosPedidosEntity> produtosPedidosEntities = produtosPedidosService.consultaPorPedido(entity.getId());
            produtosPedidosEntities.forEach(produtosPedidosEntity -> {
                Optional<ProdutoEntity> optionalProduto = produtoService.consulta(produtosPedidosEntity.getProduto().getId());
                if (optionalProduto.isEmpty())
                    throw new EntidadeNaoEncontradaException("produto não encontrado");
                MedicamentoEntity medicamentoEntity = medicamentoService.consultaPorProdutoId(optionalProduto.get().getId());
                medicamentoEntity.getAnexos().forEach(anexoEntity -> anexoIds.add(anexoEntity.getId()));
                produtos.put(optionalProduto.get(), anexoIds);
            });
            pedidos.add(factoryPedido.buildFromPedidoEntity(entity, produtos));
        });
        return pedidos;
    }

    @Transactional
    public void inicia(Long clienteId, Long farmaciaDocumento, Pedido pedido) {
        Optional<ClienteEntity> optionalCliente = clienteService.consulta(clienteId);
        if (optionalCliente.isEmpty())
            throw new EntidadeNaoEncontradaException("cliente não encontrado");

        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        PedidoEntity entity  = factoryPedidoEntity.buildFromPedidoStatusAndRelationShips(pedido,
                StatusPedidoEnum.ABERTO, optionalCliente.get(), optionalFarmacia.get());
        entity.setPreco(calculaPrecoPedido(pedido));
        pedidoRepository.save(entity);
    }

    @Transactional
    public void revisa(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        if (!entity.getStatus().equals(StatusPedidoEnum.ABERTO))
            throw new PedidoException("revisão não permitida para pedidos que não estão abertos");

        calculaPrecoPedido(pedido);
        entity.setPreco(calculaPrecoPedido(pedido));
        pedidoRepository.save(entity);
    }

    @Transactional
    public void conclui(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        if (!entity.getStatus().equals(StatusPedidoEnum.ABERTO))
            throw new PedidoException("conclusão não permitida para pedidos que não estão abertos");

        Map<ProdutoEntity, Integer> produtos = new HashMap<>();
        AtomicBoolean requerReceitaMedica = new AtomicBoolean(false);
        pedido.getProdutos().forEach((produtoid, produto) -> {
            Optional<ProdutoEntity> optionalProduto = produtoService.consulta(produtoid);
            if (optionalProduto.isEmpty())
                throw new EntidadeNaoEncontradaException("produto não encontrado");
            ProdutoEntity produtoEntity = optionalProduto.get();
            produtoService.alteraQuantidade(produtoEntity.getId(),produtoEntity.getQuantidadeEstoque() - produto.getQuantidade());
            produtos.put(produtoEntity, produto.getQuantidade());
            MedicamentoEntity medicamentoEntity = medicamentoService.consultaPorProdutoId(produtoid);
            if (!Objects.isNull(medicamentoEntity)) {
                if (medicamentoEntity.getRequerReceitaMedica())
                    requerReceitaMedica.set(true);
            }
        });

        entity.setStatus(requerReceitaMedica.get() ? StatusPedidoEnum.VALIDACAO_PENDENTE :StatusPedidoEnum.CONCLUIDO);
        entity.setPreco(calculaPrecoPedido(pedido));
        pedidoRepository.save(entity);
        if (!requerReceitaMedica.get())
            entregaService.cadastra(Entrega.builder().pedidoId(id).build());
        produtosPedidosService.cadastraQuantidades(entity, produtos);
    }

    @Transactional
    public void remove(Long id) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        if (!optional.get().getStatus().equals(StatusPedidoEnum.ABERTO))
            throw new PedidoException("remoção não permitida para pedidos que não estão abertos");

        pedidoRepository.deleteById(id);
    }

    @Transactional
    public void encerra(Long id) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        if (!entity.getStatus().equals(StatusPedidoEnum.ABERTO) &&
            !entity.getStatus().equals(StatusPedidoEnum.VALIDACAO_PENDENTE) &&
            !entity.getStatus().equals(StatusPedidoEnum.PAGAMENTO_PENDENTE) &&
            !entity.getStatus().equals(StatusPedidoEnum.CANCELADO) &&
            !entity.getStatus().equals(StatusPedidoEnum.ENTREGA_PENDENTE))
            throw new PedidoException("encerramento não permitido para pedidos abertos, cancelado ou com pendências");

        entity.setStatus(StatusPedidoEnum.ENCERRADO);
        pedidoRepository.save(entity);

        PagamentoEntity pagamentoEntity = pagamentoService.consultaPorPedido(entity.getId());
        if (Objects.isNull(pagamentoEntity))
            throw new EntidadeNaoEncontradaException("pagamento não encontrado");
        pagamentoService.alteraStatus(pagamentoEntity.getId(), StatusPagamentoEnum.PAGO);
    }

    @Transactional
    public void cancela(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        entity.setStatus(StatusPedidoEnum.CANCELADO);
        pedidoRepository.save(entity);
        pedido.getProdutos().forEach((produtoid, produto) -> {
            Optional<ProdutoEntity> optionalProduto = produtoService.consulta(produtoid);
            if (optionalProduto.isEmpty())
                throw new EntidadeNaoEncontradaException("produto não encontrado");
            ProdutoEntity produtoEntity = optionalProduto.get();
            produtoService.alteraQuantidade(produtoEntity.getId(), produtoEntity.getQuantidadeEstoque() + produto.getQuantidade());
        });
    }

    @Transactional
    public void valida(Long id, Long meioPagamentoId, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        if (!entity.getStatus().equals(StatusPedidoEnum.VALIDACAO_PENDENTE))
            throw new PedidoException("validação não permitida para pedidos que não possuem validação pendente");

        if (!pagamentoService.confirmaPagamento(entity, meioPagamentoId)) {
            entity.setStatus(StatusPedidoEnum.PAGAMENTO_PENDENTE);
            pedidoRepository.save(entity);
            throw new PagamentoException("Pagamento recusado");
        }

        entregaService.cadastra(Entrega.builder().pedidoId(id).build());
        entity.setStatus(StatusPedidoEnum.ENTREGA_PENDENTE);
        pedidoRepository.save(entity);
    }

    private Double calculaPrecoPedido(Pedido pedido) {
        AtomicReference<Double> valor = new AtomicReference<>(0.0);
        pedido.getProdutos().forEach((id, produto) -> {
            valor.set((produto.getQuantidade() * produto.getPrecoUnitario()) + valor.get());
        });
        return valor.get();
    }

}
