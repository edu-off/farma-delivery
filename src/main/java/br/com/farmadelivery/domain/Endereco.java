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
public class Endereco {

    @NotNull(message = "o campo endereço não pode ser nulo")
    @NotEmpty(message = "o campo endereço não pode ser vazio")
    private String endereco;

    @NotNull(message = "o campo bairro não pode ser nulo")
    @NotEmpty(message = "o campo bairro não pode ser vazio")
    private String bairro;

    @NotNull(message = "o campo cidade não pode ser nulo")
    @NotEmpty(message = "o campo cidade não pode ser vazio")
    private String cidade;

    @NotNull(message = "o campo uf não pode ser nulo")
    @NotEmpty(message = "o campo uf não pode ser vazio")
    private String uf;

    @NotNull(message = "o campo cep não pode ser nulo")
    @Positive(message = "o campo cep nao pode ser zero ou menor que zero")
    @Digits(integer = 8, fraction = 0, message = "o campo cep não pode ter casas decimais e não pode ter mais que 8 dígitos")
    private Integer cep;

}
