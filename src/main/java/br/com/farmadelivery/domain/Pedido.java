package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double preco;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "o campo meio pagamento não pode ser nulo")
    @NotEmpty(message = "o campo meio pagamento não pode ser vazio")
    private MeiosPagamentoEnum meioPagamento;

    @NotNull(message = "o mapa produtos não pode ser nulo")
    @NotEmpty(message = "o mapa produtos não pode ser vazio")
    private Map<Long, Produto> produtos;

}
