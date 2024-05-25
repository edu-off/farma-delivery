package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class MeioPagamento {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private MeiosPagamentoEnum meioPagamento;

}
