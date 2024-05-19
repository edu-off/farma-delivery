package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.dto.ProdutoComAnexoDto;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.exception.negocio.PagamentoException;
import br.com.farmadelivery.factory.FactoryPedidoEntity;
import br.com.farmadelivery.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private MedicamentoService medicamentoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private FactoryPedidoEntity factoryPedidoEntity;


    public Optional<PedidoEntity> consulta(Long id) {
        return pedidoRepository.findById(id);
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
                StatusPedidoEnum.ABERTO, optionalCliente.get(), optionalFarmacia.get(), listaProdutos(pedido.getProdutos()));
        pedidoRepository.save(entity);
    }

    @Transactional
    public void revisa(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        calculaPrecoPedido(pedido);
        PedidoEntity entity  = optional.get();
        entity.setPreco(pedido.getPreco());
        entity.setProdutos(listaProdutos(pedido.getProdutos()));
        pedidoRepository.save(entity);
    }

    @Transactional
    public void conclui(Long id, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        PedidoEntity entity = optional.get();
        entity.setStatus(StatusPedidoEnum.CONCLUIDO);
        subtraiEstoque(entity, pedido);
        pedidoRepository.save(entity);
        produtoService.salvaLista(entity.getProdutos());
    }

    @Transactional
    public void remove(Long id) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

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
        somaEstoque(entity, pedido);
        produtoService.salvaLista(entity.getProdutos());
    }

    @Transactional
    public void valida(Long id, Long meioPagamentoId, Pedido pedido) {
        Optional<PedidoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        medicamentoService.validaAnexos(pedido.getProdutos());
        if (pagamentoService.confirmaPagamento(optional.get(), meioPagamentoId))
            throw new PagamentoException("Pagamento recusado");
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
        pedido.getProdutos().forEach((idProduto, dto) -> {
            pedido.setPreco((dto.getProduto().getQuantidade() * dto.getProduto().getPrecoUnitario()) + pedido.getPreco());
        });
    }

    private List<ProdutoEntity> listaProdutos(Map<Long, ProdutoComAnexoDto> produtos) {
        return produtoService.retornaListaPorIds(produtos.keySet().stream().toList());
    }

    private void subtraiEstoque(PedidoEntity entity, Pedido pedido) {
        entity.getProdutos().forEach(produtoEntity -> {
            produtoEntity.setQuantidadeEstoque(produtoEntity.getQuantidadeEstoque() -
                    pedido.getProdutos().get(produtoEntity.getId()).getProduto().getQuantidade());
        });
    }

    private void somaEstoque(PedidoEntity entity, Pedido pedido) {
        entity.getProdutos().forEach(produtoEntity -> {
            produtoEntity.setQuantidadeEstoque(produtoEntity.getQuantidadeEstoque() +
                    pedido.getProdutos().get(produtoEntity.getId()).getProduto().getQuantidade());
        });
    }

}
