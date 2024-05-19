package br.com.farmadelivery.domain;

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
public class Funcionario extends Usuario {

    @NotNull(message = "o campo matricula não pode ser nulo")
    @NotEmpty(message = "o campo matricula não pode ser vazio")
    private String matricula;

}
