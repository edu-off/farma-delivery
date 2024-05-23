package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosPedidosEntityPk {

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "produto_id")
    private Long produtoId;

}
