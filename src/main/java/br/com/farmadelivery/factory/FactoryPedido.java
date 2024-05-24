package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FactoryPedido implements Factory {

    @Autowired
    private FactoryProduto factoryProduto;

    public Pedido buildFromPedidoEntity(PedidoEntity pedidoEntity, List<Long> anexosIds, List<ProdutoEntity> produtosEntities) {
        Map<Long, Produto> produtos = new HashMap<>();
        produtosEntities.forEach(produtoEntity -> {
            produtos.put(produtoEntity.getId(), factoryProduto.buildFromProdutoEntity(produtoEntity, anexosIds));
        });

        return Pedido.builder()
                .preco(pedidoEntity.getPreco())
                .meioPagamento(pedidoEntity.getPagamento().getMeioPagamento().getMeioPagamento())
                .produtos(produtos)
                .build();
    }

}
