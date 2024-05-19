package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.FuncionarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryUsuario implements Factory {

    public Usuario buildFromCliente(Cliente cliente) {
        return Usuario.builder()
                .documento(cliente.getDocumento())
                .tipoPessoa(cliente.getTipoPessoa())
                .nome(cliente.getNome())
                .status(cliente.getStatus())
                .apelido(cliente.getApelido())
                .email(cliente.getEmail())
                .senha(cliente.getSenha())
                .build();
    }

    public Usuario buildFromFuncionario(Funcionario funcionario) {
        return Usuario.builder()
                .documento(funcionario.getDocumento())
                .tipoPessoa(funcionario.getTipoPessoa())
                .nome(funcionario.getNome())
                .status(funcionario.getStatus())
                .apelido(funcionario.getApelido())
                .email(funcionario.getEmail())
                .senha(funcionario.getSenha())
                .build();
    }

    public Usuario buildFromEntregador(Entregador entregador) {
        return Usuario.builder()
                .documento(entregador.getDocumento())
                .tipoPessoa(entregador.getTipoPessoa())
                .nome(entregador.getNome())
                .status(entregador.getStatus())
                .apelido(entregador.getApelido())
                .email(entregador.getEmail())
                .senha(entregador.getSenha())
                .build();
    }

    public Usuario buildFromClienteEntity(ClienteEntity entity) {
        return Usuario.builder()
                .documento(entity.getUsuario().getId().getDocumento())
                .tipoPessoa(entity.getUsuario().getId().getTipoPessoa())
                .nome(entity.getUsuario().getNome())
                .status(entity.getUsuario().getStatus())
                .apelido(entity.getUsuario().getApelido())
                .email(entity.getUsuario().getEmail())
                .senha(entity.getUsuario().getSenha())
                .build();
    }

    public Usuario buildFromFuncionarioEntity(FuncionarioEntity entity) {
        return Usuario.builder()
                .documento(entity.getUsuario().getId().getDocumento())
                .tipoPessoa(entity.getUsuario().getId().getTipoPessoa())
                .nome(entity.getUsuario().getNome())
                .status(entity.getUsuario().getStatus())
                .apelido(entity.getUsuario().getApelido())
                .email(entity.getUsuario().getEmail())
                .senha(entity.getUsuario().getSenha())
                .build();
    }

    public Usuario buildFromEntregadorEntity(EntregadorEntity entity) {
        return Usuario.builder()
                .documento(entity.getUsuario().getId().getDocumento())
                .tipoPessoa(entity.getUsuario().getId().getTipoPessoa())
                .nome(entity.getUsuario().getNome())
                .status(entity.getUsuario().getStatus())
                .apelido(entity.getUsuario().getApelido())
                .email(entity.getUsuario().getEmail())
                .senha(entity.getUsuario().getSenha())
                .build();
    }

}
