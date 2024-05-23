package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.entity.ProdutosPedidosEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryQuantidadeProdutoPorPedido implements Factory {

    public ProdutosPedidosEntity build(PedidoEntity pedidoEntity,
                                                                 ProdutoEntity produtoEntity,
                                                                 Integer quantidade) {
        return ProdutosPedidosEntity.builder()
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .quantidade(quantidade)
                .build();
    }

}
