package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.dto.CadastroFarmaciaDto;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
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
