package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusAtivacaoEnum status;

    private String nome;

    private String descricao;

    @Column(name = "preco_unitario")
    private Double precoUnitario;

    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @ManyToOne
    @JoinColumn(name = "farmacia_documento", referencedColumnName = "documento")
    private FarmaciaEntity farmacia;

    @OneToOne(mappedBy = "produto")
    private MedicamentoEntity medicamento;

    @ManyToMany
    @JoinTable(name = "pedido_produto",
            joinColumns = {@JoinColumn(name = "pedido_id")},
            inverseJoinColumns = {@JoinColumn(name = "produto_id")})
    private List<PedidoEntity> pedidos;

    @ManyToOne
    @JoinColumn(name = "secao_id", referencedColumnName = "id")
    private SecaoEntity secao;

}