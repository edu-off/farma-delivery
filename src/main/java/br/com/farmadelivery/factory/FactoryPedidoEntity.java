package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryPedidoEntity implements Factory {

    public PedidoEntity buildFromPedidoStatusAndRelationShips(Pedido pedido,
                                                              StatusPedidoEnum status,
                                                              ClienteEntity clienteEntity,
                                                              FarmaciaEntity farmaciaEntity) {
        return PedidoEntity.builder()
                .status(status)
                .cliente(clienteEntity)
                .farmacia(farmaciaEntity)
                .build();
    }

}
