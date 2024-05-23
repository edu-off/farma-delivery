package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos_pedidos")
public class ProdutosPedidosEntity {

    @EmbeddedId
    private ProdutosPedidosEntityPk id;

    private Integer quantidade;

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private ProdutoEntity produto;

}
