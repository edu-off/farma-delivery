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
public class Medicamento extends Produto {

    @NotNull(message = "o campo dosagem não pode ser nulo")
    @Positive(message = "o campo dosagem nao pode ser zero ou menor que zero")
    @Digits(integer = 3, fraction = 2, message = "o campo dosagem pode ter somente 2 casas decimais e não pode ter mais que 3 dígitos")
    private Double dosagem;

    @NotNull(message = "o campo laboratorio não pode ser nulo")
    @NotEmpty(message = "o campo laboratorio não pode ser vazio")
    private String laboratorio;

    @NotNull(message = "o campo requer receita médica não pode ser nulo")
    @NotEmpty(message = "o campo requer receita médica não pode ser vazio")
    private Boolean requerReceitaMedica;

}
