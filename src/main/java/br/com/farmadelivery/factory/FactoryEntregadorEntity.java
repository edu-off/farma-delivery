package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryEntregadorEntity implements Factory {

    public EntregadorEntity buildEntregador(Entregador entregador, UsuarioEntity usuarioEntity) {
        return EntregadorEntity.builder()
                .estaAlocado(entregador.isEstaAlocado())
                .usuario(usuarioEntity)
                .build();
    }

}
