package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.UsuarioEntityPk;
import br.com.farmadelivery.enums.TiposPessoaEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryUsuarioEntityPk implements Factory {

    public UsuarioEntityPk buildFromDadosId(TiposPessoaEnum tipoPessoa, Long documento, TiposUsuarioEnum tiposUsuario) {
        return UsuarioEntityPk.builder()
                .tipoPessoa(tipoPessoa)
                .documento(documento)
                .tipo(tiposUsuario)
                .build();
    }

}
