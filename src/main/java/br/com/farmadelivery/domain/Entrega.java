package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.StatusEntregaEnum;
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
public class Entrega {

    private Entregador entregador;
    private Long pedidoId;

}
