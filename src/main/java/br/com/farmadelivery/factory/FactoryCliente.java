package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryCliente implements Factory {

    @Autowired
    private FactoryEndereco factoryEndereco;

    public Cliente buildFromClienteEntity(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .ddd(clienteEntity.getDdd())
                .telefone(clienteEntity.getTelefone())
                .endereco(factoryEndereco.buildFromEnderecoEntity(clienteEntity.getEndereco()))
                .documento(clienteEntity.getUsuario().getId().getDocumento())
                .tipoPessoa(clienteEntity.getUsuario().getId().getTipoPessoa())
                .nome(clienteEntity.getUsuario().getNome())
                .status(clienteEntity.getUsuario().getStatus())
                .apelido(clienteEntity.getUsuario().getApelido())
                .email(clienteEntity.getUsuario().getEmail())
                .senha(clienteEntity.getUsuario().getSenha())
                .build();
    }

}
