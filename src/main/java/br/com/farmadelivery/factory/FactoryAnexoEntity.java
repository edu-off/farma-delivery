package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.AnexoEntity;
import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.enums.TiposAnexoEnum;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Blob;

@Component
public class FactoryAnexoEntity implements Factory {

    public AnexoEntity build(MedicamentoEntity medicamentoEntity, TiposAnexoEnum tipo, Blob anexo) {
        return AnexoEntity.builder()
                .tipo(tipo)
                .anexo(anexo)
                .medicamento(medicamentoEntity)
                .build();
    }

}
