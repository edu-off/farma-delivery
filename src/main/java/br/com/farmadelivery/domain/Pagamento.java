package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.StatusPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    private StatusPagamentoEnum status;
    private MeioPagamento meioPagamento;

}
