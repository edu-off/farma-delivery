package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.*;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FuncionarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryProduto implements Factory {

    public Produto buildFromMedicamento(Medicamento medicamento) {
        return Produto.builder()
                .status(medicamento.getStatus())
                .nome(medicamento.getNome())
                .descricao(medicamento.getDescricao())
                .precoUnitario(medicamento.getPrecoUnitario())
                .quantidade(medicamento.getQuantidade())
                .build();
    }

}
