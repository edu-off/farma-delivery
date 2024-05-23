package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MeioPagamentoCartaoCredito.class, name = "cartaoCredito")
})
public abstract class MeioPagamento {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private MeiosPagamentoEnum meioPagamento;

}
