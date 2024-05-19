package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.entity.ClienteEntity;
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
