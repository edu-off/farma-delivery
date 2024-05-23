package br.com.farmadelivery.service;

import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.entity.ProdutosPedidosEntity;
import br.com.farmadelivery.factory.FactoryQuantidadeProdutoPorPedido;
import br.com.farmadelivery.repository.ProdutosPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProdutosPedidosService {

    @Autowired
    private ProdutosPedidosRepository produtosPedidosEntityRepository;

    @Autowired
    private FactoryQuantidadeProdutoPorPedido factoryQuantidadeProdutoPorPedido;

    public List<ProdutosPedidosEntity> consultaPorPedido(Long pedidoId) {
        return produtosPedidosEntityRepository.findAllByPedidoId(pedidoId);
    }

    @Transactional
    public void cadastraQuantidades(PedidoEntity pedido, Map<Integer, ProdutoEntity> produtos) {
        produtos.forEach((quantidade, produto)-> {
            ProdutosPedidosEntity entity = factoryQuantidadeProdutoPorPedido.build(pedido, produto, quantidade);
            produtosPedidosEntityRepository.save(entity);
        });
    }

}
