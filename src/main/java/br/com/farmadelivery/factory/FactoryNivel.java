package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.entity.NivelEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryNivel implements Factory {

    public Nivel build(NivelEntity nivelEntity) {
        return Nivel.builder()
                .id(nivelEntity.getId())
                .nome(nivelEntity.getNome())
                .descricao(nivelEntity.getDescricao())
                .build();
    }

    public Nivel build(String nome, String descricao) {
        return Nivel.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }


}
