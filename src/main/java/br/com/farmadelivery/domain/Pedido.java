package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
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

    @NotNull(message = "o campo preço não pode ser nulo")
    @Positive(message = "o campo preço nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 2, message = "o campo preço pode ter somente 2 casas decimais e não pode ter mais que 7 dígitos")
    private Double preco;

    @NotNull(message = "o campo meio pagamento não pode ser nulo")
    @NotEmpty(message = "o campo meio pagamento não pode ser vazio")
    private MeiosPagamentoEnum meioPagamento;

    @NotNull(message = "o mapa produtos não pode ser nulo")
    @NotEmpty(message = "o mapa produtos não pode ser vazio")
    private Map<Long, Produto> produtos;

}
