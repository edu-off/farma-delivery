package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class UsuarioEntity {

    @EmbeddedId
    private UsuarioEntityPk id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusAtivacaoEnum status;

    private String apelido;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    @OneToOne(mappedBy = "usuario")
    private FuncionarioEntity funcionario;

    @OneToOne(mappedBy = "usuario")
    private ClienteEntity cliente;

    @OneToOne(mappedBy = "usuario")
    private EntregadorEntity entregador;

}
