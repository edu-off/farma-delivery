package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.entity.EntregaEntity;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.StatusEntregaEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryEntregaEntity implements Factory {

    public EntregaEntity buildFromEntrega(StatusEntregaEnum status, Entrega entrega,
                                          PedidoEntity pedidoEntity,
                                          EntregadorEntity entregadorEntity) {
        return EntregaEntity.builder()
                .status(status)
                .entregador(entregadorEntity)
                .pedido(pedidoEntity)
                .build();
    }

}
