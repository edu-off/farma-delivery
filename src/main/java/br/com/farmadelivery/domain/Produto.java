package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Produto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> anexosId;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private StatusAtivacaoEnum status;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo descrição não pode ser nulo")
    @NotEmpty(message = "o campo descrição não pode ser vazio")
    private String descricao;

    @NotNull(message = "o campo preço unitário não pode ser nulo")
    @Positive(message = "o campo preço unitário nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 2, message = "o campo preço unitário pode ter somente 2 casas decimais e não pode ter mais que 7 dígitos")
    private Double precoUnitario;

    @NotNull(message = "o campo quantidade não pode ser nulo")
    @Positive(message = "o campo quantidade nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 0, message = "o campo quantidade não ppode casas decimais e não pode ter mais que 7 dígitos")
    private Integer quantidade;

}
