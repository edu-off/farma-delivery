package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactoryPedidoEntity implements Factory {

    public PedidoEntity buildFromPedidoStatusAndRelationShips(Pedido pedido,
                                                              StatusPedidoEnum status,
                                                              ClienteEntity clienteEntity,
                                                              FarmaciaEntity farmaciaEntity) {
        return PedidoEntity.builder()
                .status(status)
                .preco(pedido.getPreco())
                .cliente(clienteEntity)
                .farmacia(farmaciaEntity)
                .build();
    }

}
