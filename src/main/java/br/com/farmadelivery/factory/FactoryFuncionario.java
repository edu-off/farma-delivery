package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FuncionarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryFuncionario implements Factory {

    public Funcionario buildFromFuncionarioEntity(FuncionarioEntity funcionarioEntity) {
        return Funcionario.builder()
                .matricula(funcionarioEntity.getId().getMatricula())
                .documento(funcionarioEntity.getUsuario().getId().getDocumento())
                .tipoPessoa(funcionarioEntity.getUsuario().getId().getTipoPessoa())
                .nome(funcionarioEntity.getUsuario().getNome())
                .status(funcionarioEntity.getUsuario().getStatus())
                .apelido(funcionarioEntity.getUsuario().getApelido())
                .email(funcionarioEntity.getUsuario().getEmail())
                .senha(funcionarioEntity.getUsuario().getSenha())
                .build();
    }

}
