package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryMedicamento implements Factory {

    public Medicamento buildFromMedicamentoEntityAndProdutoEntity(MedicamentoEntity medicamentoEntity, ProdutoEntity produtoEntity) {
        return Medicamento.builder()
                .id(medicamentoEntity.getId())
                .status(produtoEntity.getStatus())
                .nome(produtoEntity.getNome())
                .descricao(produtoEntity.getDescricao())
                .precoUnitario(produtoEntity.getPrecoUnitario())
                .quantidade(produtoEntity.getQuantidadeEstoque())
                .dosagem(medicamentoEntity.getDosagem())
                .laboratorio(medicamentoEntity.getLaboratorio())
                .requerReceitaMedica(medicamentoEntity.getRequerReceitaMedica())
                .build();
    }

}
