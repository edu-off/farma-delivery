package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entregador")
public class EntregadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "esta_alocado")
    private boolean estaAlocado;

    @OneToOne
    @JoinColumn(name = "usuario_documento", referencedColumnName = "documento")
    @JoinColumn(name = "usuario_tipo_pessoa", referencedColumnName = "tipo_pessoa")
    @JoinColumn(name = "usuario_tipo", referencedColumnName = "tipo")
    private UsuarioEntity usuario;

    @OneToOne(mappedBy = "entregador")
    private EntregaEntity entrega;

}
