package br.com.farmadelivery.dto;

import br.com.farmadelivery.domain.Farmacia;
import br.com.farmadelivery.domain.Funcionario;
import jakarta.validation.Valid;
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
public class CadastroFarmaciaDto {

    @Valid
    @NotNull(message = "Objeto farmacia não pode ser nulo")
    private Farmacia farmacia;

    @Valid
    @NotNull(message = "Objeto funcionario não pode ser nulo")
    private Funcionario funcionario;

}
