package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryEntregador implements Factory {

    public Entregador buildFromEntregadorEntity(EntregadorEntity entregadorEntity, UsuarioEntity usuarioEntity) {
        return Entregador.builder()
                .estaAlocado(entregadorEntity.isEstaAlocado())
                .documento(usuarioEntity.getId().getDocumento())
                .tipoPessoa(usuarioEntity.getId().getTipoPessoa())
                .nome(usuarioEntity.getNome())
                .status(usuarioEntity.getStatus())
                .apelido(usuarioEntity.getApelido())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .build();
    }

}
