package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.MeioPagamentoEntity;
import br.com.farmadelivery.entity.PagamentoEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.StatusPagamentoEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryPagamentoEntity implements Factory {

    public PagamentoEntity build(StatusPagamentoEnum status, MeioPagamentoEntity meioPagamentoEntity, PedidoEntity pedidoEntity) {
        return PagamentoEntity.builder()
                .status(status)
                .meioPagamento(meioPagamentoEntity)
                .pedido(pedidoEntity)
                .build();
    }

}
