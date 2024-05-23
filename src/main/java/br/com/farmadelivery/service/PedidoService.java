package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.entity.*;
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
            List<Long> anexosId = new ArrayList<>();
            List<ProdutoEntity> produtos = new ArrayList<>();
            entity.getProdutos().forEach(quantidadeProdutoPorPedidoEntity -> {
                Optional<ProdutoEntity> optionalProduto = produtoService.consulta(quantidadeProdutoPorPedidoEntity.getProduto().getId());
                if (optionalProduto.isEmpty())
                    throw new EntidadeNaoEncontradaException("produto não encontrado");
                MedicamentoEntity medicamentoEntity = medicamentoService.consultaPorProdutoId(optionalProduto.get().getId());
                medicamentoEntity.getAnexos().forEach(anexoEntity -> {
                    anexosId.add(anexoEntity.getId());
                });
            });
            pedidos.add(factoryPedido.buildFromPedidoEntity(entity, anexosId, produtos));
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

        calculaPrecoPedido(pedido);
        PedidoEntity entity  = factoryPedidoEntity.buildFromPedidoStatusAndRelationShips(pedido,
                StatusPedidoEnum.ABERTO, optionalCliente.get(), optionalFarmacia.get());

        pedidoRepository.save(entity);
    }

    @Transactional
    public void revisa(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity  = optional.get();
        calculaPrecoPedido(pedido);
        entity.setPreco(pedido.getPreco());
        pedidoRepository.save(entity);
    }

    @Transactional
    public void conclui(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        List<ProdutoEntity> produtoEntities = new ArrayList<>();
        Map<Integer, ProdutoEntity> produtos = new HashMap<>();
        AtomicBoolean requerReceitaMedica = new AtomicBoolean(false);
        pedido.getProdutos().forEach((produtoid, produto) -> {
            Optional<ProdutoEntity> optionalProduto = produtoService.consulta(produtoid);
            if (optionalProduto.isEmpty())
                throw new EntidadeNaoEncontradaException("produto não encontrado");
            ProdutoEntity produtoEntity = optionalProduto.get();
            produtoEntities.add(produtoService.alteraQuantidade(produtoEntity.getId(), produto.getQuantidade()));
            produtos.put(produto.getQuantidade(), produtoEntity);
            MedicamentoEntity medicamentoEntity = medicamentoService.consultaPorProdutoId(produtoid);
            if (!Objects.isNull(medicamentoEntity)) {
                if (medicamentoEntity.getRequerReceitaMedica())
                    requerReceitaMedica.set(true);
            }
        });

        entity.setStatus(requerReceitaMedica.get() ? StatusPedidoEnum.VALIDACAO_PENDENTE :StatusPedidoEnum.CONCLUIDO);
        produtoService.salvaLista(produtoEntities);
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
            throw new PedidoException("remoção não permitida pedidos que foram concluídos");

        pedidoRepository.deleteById(id);
    }

    @Transactional
    public void encerra(Long id) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        entity.setStatus(StatusPedidoEnum.ENCERRADO);
        pedidoRepository.save(entity);
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
            produtoService.alteraQuantidade(produtoEntity.getId(), produto.getQuantidade());
        });
    }

    @Transactional
    public void valida(Long id, Long meioPagamentoId, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        if (pagamentoService.confirmaPagamento(entity, meioPagamentoId)) {
            entity.setStatus(StatusPedidoEnum.PAGAMENTO_PENDENTE);
            pedidoRepository.save(entity);
            throw new PagamentoException("Pagamento recusado");
        }

        entregaService.cadastra(Entrega.builder().pedidoId(id).build());
        entity.setStatus(StatusPedidoEnum.ENTREGA_PENDENTE);
        pedidoRepository.save(entity);
    }

    @Transactional
    public void invalida(Long id) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        entity.setStatus(StatusPedidoEnum.CANCELADO);
        pedidoRepository.save(entity);
    }

    private void calculaPrecoPedido(Pedido pedido) {
        pedido.getProdutos().forEach((id, produto) -> {
            pedido.setPreco((produto.getQuantidade() * produto.getPrecoUnitario()) + pedido.getPreco());
        });
    }

}
