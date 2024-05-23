package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    private Double preco;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "farmacia_documento", referencedColumnName = "documento")
    private FarmaciaEntity farmacia;

    @OneToOne(mappedBy = "pedido")
    private PagamentoEntity pagamento;

    @OneToOne(mappedBy = "pedido")
    private EntregaEntity entrega;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<ProdutosPedidosEntity> produtos = new ArrayList<>();

}
