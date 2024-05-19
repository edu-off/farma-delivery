package br.com.farmadelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioEntityPk implements Serializable {

    @Column(name = "farmacia_documento")
    private Long farmaciaDocumento;
    private String matricula;

}
