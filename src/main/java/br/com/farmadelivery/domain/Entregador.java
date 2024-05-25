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
public class Entregador extends Usuario {

    @NotNull(message = "o campo esta alocado não pode ser nulo")
    @NotEmpty(message = "o campo esta alocado não pode ser vazio")
    private boolean estaAlocado;

}
