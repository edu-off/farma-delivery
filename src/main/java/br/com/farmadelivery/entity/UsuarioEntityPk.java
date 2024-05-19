package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.TiposPessoaEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntityPk implements Serializable {

    private Long documento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TiposPessoaEnum tipoPessoa;

    @Enumerated(EnumType.STRING)
    private TiposUsuarioEnum tipo;

}
