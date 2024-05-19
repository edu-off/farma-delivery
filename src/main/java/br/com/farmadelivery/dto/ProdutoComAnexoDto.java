package br.com.farmadelivery.dto;

import br.com.farmadelivery.domain.Anexo;
import br.com.farmadelivery.domain.Farmacia;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.domain.Produto;
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
public class ProdutoComAnexoDto {

    @Valid
    @NotNull(message = "Objeto produto não pode ser nulo")
    private Produto produto;

    private Anexo anexo;

}
