package br.com.farmadelivery.domain;

import jakarta.validation.Valid;
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
public class Farmacia {

    @NotNull(message = "o campo documento não pode ser nulo")
    @Positive(message = "o campo documento nao pode ser zero ou menor que zero")
    @Digits(integer = 14, fraction = 0, message = "o campo documento não pode ter casas decimais e não pode ter mais que 14 dígitos")
    private Long documento;

    @NotNull(message = "o campo razao social não pode ser nulo")
    @NotEmpty(message = "o campo razao social não pode ser vazio")
    private String razaoSocial;

    @Valid
    @NotNull(message = "Objeto endereço não pode ser null")
    private Endereco endereco;

}
