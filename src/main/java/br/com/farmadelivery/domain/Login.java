package br.com.farmadelivery.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Login {

    @NotNull(message = "o campo login não pode ser nulo")
    @NotEmpty(message = "o campo login não pode ser vazio")
    @Email(message = "o campo email deve ter formato de email válido")
    private String login;

    @NotNull(message = "o campo senha não pode ser nulo")
    @NotEmpty(message = "o campo senha não pode ser vazio")
    private String senha;

}
