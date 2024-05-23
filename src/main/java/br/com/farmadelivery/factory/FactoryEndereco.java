package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Endereco;
import br.com.farmadelivery.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryEndereco implements Factory {

    public Endereco buildFromEnderecoEntity(EnderecoEntity enderecoEntity) {
        return Endereco.builder()
                .endereco(enderecoEntity.getEndereco())
                .bairro(enderecoEntity.getBairro())
                .cidade(enderecoEntity.getCidade())
                .uf(enderecoEntity.getUf())
                .cep(enderecoEntity.getCep())
                .build();
    }


}
