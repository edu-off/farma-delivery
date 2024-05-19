package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funcionario")
public class FuncionarioEntity {

    @EmbeddedId
    private FuncionarioEntityPk id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_documento", referencedColumnName = "documento")
    @JoinColumn(name = "usuario_tipo_pessoa", referencedColumnName = "tipo_pessoa")
    @JoinColumn(name = "usuario_tipo", referencedColumnName = "tipo")
    private UsuarioEntity usuario;

    @ManyToOne
    @MapsId("farmaciaDocumento")
    @JoinColumn(name = "farmacia_documento", referencedColumnName = "documento")
    private FarmaciaEntity farmacia;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "nivel_id", referencedColumnName = "id")
    private NivelEntity nivel;

}
