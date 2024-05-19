package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.enums.TiposPessoaEnum;
import jakarta.validation.constraints.*;
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
public class Usuario {

    @NotNull(message = "o campo documento não pode ser nulo")
    @Positive(message = "o campo documento nao pode ser zero ou menor que zero")
    @Digits(integer = 14, fraction = 0, message = "o campo documento não pode ter casas decimais e não pode ter mais que 14 dígitos")
    private Long documento;

    @NotNull(message = "o campo tipo pessoa não pode ser nulo")
    @NotEmpty(message = "o campo tipo pessoa não pode ser vazio")
    private TiposPessoaEnum tipoPessoa;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private StatusAtivacaoEnum status;

    @NotNull(message = "o campo apelido não pode ser nulo")
    @NotEmpty(message = "o campo apelido não pode ser vazio")
    private String apelido;

    @NotNull(message = "o campo email não pode ser nulo")
    @NotEmpty(message = "o campo email não pode ser vazio")
    @Email(message = "o campo email deve ter formato de email válido")
    private String email;

    @NotNull(message = "o campo senha não pode ser nulo")
    @NotEmpty(message = "o campo senha não pode ser vazio")
    private String senha;

}
