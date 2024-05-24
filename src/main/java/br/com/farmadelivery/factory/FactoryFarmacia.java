package br.com.farmadelivery.factory;

import br.com.farmadelivery.dto.CadastroFarmaciaDto;
import br.com.farmadelivery.entity.FarmaciaEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryFarmacia implements Factory {

    public FarmaciaEntity buildFromDto(CadastroFarmaciaDto dto) {
        return FarmaciaEntity.builder()
                .documento(dto.getFarmacia().getDocumento())
                .razaoSocial(dto.getFarmacia().getRazaoSocial())
                .build();
    }

}
