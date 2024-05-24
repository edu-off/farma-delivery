package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Endereco;
import br.com.farmadelivery.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryEnderecoEntity implements Factory {

    public EnderecoEntity buildFromEndereco(Endereco endereco) {
        return EnderecoEntity.builder()
                .endereco(endereco.getEndereco())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .cep(endereco.getCep())
                .build();
    }


}
