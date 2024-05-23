package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.TiposAnexoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.jdbc.BlobProxy;

import java.io.File;
import java.sql.Blob;

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

    @Enumerated(EnumType.ORDINAL)
    private TiposAnexoEnum tipo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Blob anexo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", referencedColumnName = "id")
    private MedicamentoEntity medicamento;


}
