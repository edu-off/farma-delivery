package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Nivel;
import org.springframework.stereotype.Component;

@Component
public class FactoryNivel implements Factory {

    public Nivel build(String nome, String descricao) {
        return Nivel.builder()
                .nome(nome)
                .descricao(descricao)
                .build();
    }

}
