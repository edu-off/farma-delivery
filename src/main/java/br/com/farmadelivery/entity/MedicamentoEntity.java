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
@Table(name = "medicamento")
public class MedicamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double dosagem;

    private String laboratorio;

    @Column(name = "requer_receita_medica")
    private Boolean requerReceitaMedica;

    @OneToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private ProdutoEntity produto;

    @OneToMany(mappedBy = "medicamento", fetch = FetchType.LAZY)
    private List<AnexoEntity> anexos = new ArrayList<>();

}
