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

    public Pedido buildFromPedidoEntity(PedidoEntity pedidoEntity, Map<ProdutoEntity, List<Long>> produtoEntities) {
        Map<Long, Produto> produtos = new HashMap<>();
        produtoEntities.forEach((produtoEntity, anexoIds) -> {
            Produto produto = factoryProduto.buildFromProdutoEntity(produtoEntity, anexoIds);
            produto.setId(produtoEntity.getId());
            produtos.put(produtoEntity.getId(), produto);
        });

        return Pedido.builder()

                .preco(pedidoEntity.getPreco())
                .produtos(produtos)
                .build();
    }

}
