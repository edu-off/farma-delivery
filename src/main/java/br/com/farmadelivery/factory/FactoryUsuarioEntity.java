package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FuncionarioEntity;
import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.entity.UsuarioEntityPk;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryUsuarioEntity implements Factory {

    public UsuarioEntity buildFromUsuarioEntityPkAndUsuario(UsuarioEntityPk pk, Usuario usuario) {
        return UsuarioEntity.builder().id(pk)
                .nome(usuario.getNome())
                .apelido(usuario.getApelido())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

}
