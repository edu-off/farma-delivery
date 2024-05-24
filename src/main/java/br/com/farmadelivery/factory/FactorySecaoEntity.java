package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Secao;
import br.com.farmadelivery.entity.SecaoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactorySecaoEntity implements Factory {

    public SecaoEntity build(Secao secao) {
        return SecaoEntity.builder()
                .nome(secao.getNome())
                .descricao(secao.getDescricao())
                .build();
    }

}
