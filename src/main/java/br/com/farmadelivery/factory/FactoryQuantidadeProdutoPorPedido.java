package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.entity.ProdutosPedidosEntity;
import br.com.farmadelivery.entity.ProdutosPedidosEntityPk;
import org.springframework.stereotype.Component;

@Component
public class FactoryQuantidadeProdutoPorPedido implements Factory {

    public ProdutosPedidosEntity build(PedidoEntity pedidoEntity,
                                       ProdutoEntity produtoEntity,
                                       Integer quantidade) {
        ProdutosPedidosEntityPk pk = ProdutosPedidosEntityPk.builder()
                .pedidoId(pedidoEntity.getId())
                .produtoId(produtoEntity.getId())
                .build();
        return ProdutosPedidosEntity.builder()
                .id(pk)
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .quantidade(quantidade)
                .build();
    }

}
