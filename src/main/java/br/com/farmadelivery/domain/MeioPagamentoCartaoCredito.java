package br.com.farmadelivery.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class MeioPagamentoCartaoCredito extends MeioPagamento {

    @NotNull(message = "o campo nome completo não pode ser nulo")
    @NotEmpty(message = "o campo nome completo não pode ser vazio")
    private String nomeCompleto;

    @NotNull(message = "o campo número não pode ser nulo")
    @Positive(message = "o campo número nao pode ser zero ou menor que zero")
    @Digits(integer = 16, fraction = 0, message = "o campo número não pode ter casas decimais e não pode ter mais que 16 dígitos")
    private Long numero;

    @NotNull(message = "o campo data vencimento não pode ser nulo")
    @NotEmpty(message = "o campo data vencimento não pode ser vazio")
    private String dataVencimento;

    @NotNull(message = "o campo código segurança não pode ser nulo")
    @Positive(message = "o campo código segurança nao pode ser zero ou menor que zero")
    @Digits(integer = 3, fraction = 0, message = "o campo código segurança não pode ter casas decimais e não pode ter mais que 3 dígitos")
    private Integer codigoSeguranca;

}
