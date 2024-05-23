package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Secao;
import br.com.farmadelivery.entity.SecaoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactorySecao implements Factory {

    public Secao build(SecaoEntity secaoEntity) {
        return Secao.builder()
                .id(secaoEntity.getId())
                .nome(secaoEntity.getNome())
                .descricao(secaoEntity.getDescricao())
                .build();
    }

}
