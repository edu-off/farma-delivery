package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Anexo;
import br.com.farmadelivery.domain.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class FactoryAnexo implements Factory {

    public Anexo buildFromMedicamento(Medicamento medicamento) {
        return Anexo.builder()
                .status(medicamento.getStatus())
                .nome(medicamento.getNome())
                .descricao(medicamento.getDescricao())
                .precoUnitario(medicamento.getPrecoUnitario())
                .quantidade(medicamento.getQuantidade())
                .dosagem(medicamento.getDosagem())
                .laboratorio(medicamento.getLaboratorio())
                .requerReceitaMedica(medicamento.getRequerReceitaMedica())
                .build();
    }

}
