package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.NivelEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryNivelEntity implements Factory {

    public NivelEntity build(Nivel nivel, FarmaciaEntity farmaciaEntity) {
        return NivelEntity.builder()
                .nome(nivel.getNome())
                .descricao(nivel.getDescricao())
                .farmacia(farmaciaEntity)
                .build();
    }

}
