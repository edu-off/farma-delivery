package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.FuncionarioEntityPk;
import org.springframework.stereotype.Component;

@Component
public class FactoryFuncionarioEntityPk implements Factory {

    public FuncionarioEntityPk buildFromDadosId(String matricula, Long farmaciaDocumento) {
        return FuncionarioEntityPk.builder()
                .farmaciaDocumento(farmaciaDocumento)
                .matricula(matricula)
                .build();
    }

}
