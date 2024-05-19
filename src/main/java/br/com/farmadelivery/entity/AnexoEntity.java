package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "anexo")
public class AnexoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receitaMedica")
    private File receitaMedica;

    @Column(name = "documento_com_foto")
    private File documentoComFoto;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", referencedColumnName = "id")
    private MedicamentoEntity medicamento;


}
