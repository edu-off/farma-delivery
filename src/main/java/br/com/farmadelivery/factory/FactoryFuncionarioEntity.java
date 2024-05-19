package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.*;
import org.springframework.stereotype.Component;

@Component
public class FactoryFuncionarioEntity implements Factory {

    public FuncionarioEntity buildFromVariosDados(FuncionarioEntityPk pk, UsuarioEntity usuarioEntity,
                                                  FarmaciaEntity farmaciaEntity, NivelEntity nivelEntity) {
        return FuncionarioEntity.builder().id(pk)
                .usuario(usuarioEntity)
                .farmacia(farmaciaEntity)
                .nivel(nivelEntity)
                .build();
    }

}
