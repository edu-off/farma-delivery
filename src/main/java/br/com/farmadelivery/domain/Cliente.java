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

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @NotNull(message = "o campo ddd não pode ser nulo")
    @Positive(message = "o campo ddd nao pode ser zero ou menor que zero")
    @Digits(integer = 2, fraction = 0, message = "o campo ddd não pode ter casas decimais e não pode ter mais que 2 dígitos")
    private Integer ddd;

    @NotNull(message = "o campo telefone não pode ser nulo")
    @Positive(message = "o campo telefone nao pode ser zero ou menor que zero")
    @Digits(integer = 9, fraction = 0, message = "o campo telefone não pode ter casas decimais e não pode ter mais que 9 dígitos")
    private Long telefone;

    @Valid
    @NotNull(message = "objeto endereco não pode ser nulo")
    private Endereco endereco;

}
