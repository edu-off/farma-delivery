package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryClienteEntity implements Factory {

    public ClienteEntity buildFromCliente(Cliente cliente) {
        return ClienteEntity.builder()
                .ddd(cliente.getDdd())
                .telefone(cliente.getTelefone())
                .build();
    }

}
