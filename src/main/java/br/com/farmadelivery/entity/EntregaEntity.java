package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entrega")
public class EntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum status;

    @OneToOne
    @JoinColumn(name = "entregador_id", referencedColumnName = "id")
    private EntregadorEntity entregador;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

}
