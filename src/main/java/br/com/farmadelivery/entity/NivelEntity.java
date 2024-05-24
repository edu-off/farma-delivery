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
@Table(name = "nivel")
public class NivelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "farmacia_documento", referencedColumnName = "documento")
    private FarmaciaEntity farmacia;

    @OneToMany(mappedBy = "nivel", fetch = FetchType.LAZY)
    private List<FuncionarioEntity> funcionarios = new ArrayList<>();

}
