package br.com.farmadelivery.entity;

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
@Table(name = "secao")
public class SecaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "farmacia_documento", referencedColumnName = "documento")
    private FarmaciaEntity farmacia;

    @OneToMany(mappedBy = "secao", fetch = FetchType.LAZY)
    private List<ProdutoEntity> produtos = new ArrayList<>();

}
