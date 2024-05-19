package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryMedicamentoEntity implements Factory {

    public MedicamentoEntity buildFromMedicamentoAndProduto(Medicamento medicamento, ProdutoEntity produtoEntity) {
        return MedicamentoEntity.builder()
                .dosagem(medicamento.getDosagem())
                .laboratorio(medicamento.getLaboratorio())
                .requerReceitaMedica(medicamento.getRequerReceitaMedica())
                .produto(produtoEntity)
                .build();
    }

}
