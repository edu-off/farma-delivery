package br.com.farmadelivery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.File;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Secao {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo descrição não pode ser nulo")
    @NotEmpty(message = "o campo descrição não pode ser vazio")
    private String descricao;

}
