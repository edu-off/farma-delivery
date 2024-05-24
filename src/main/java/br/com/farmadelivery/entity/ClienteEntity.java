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
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ddd;

    private Long telefone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_documento", referencedColumnName = "documento")
    @JoinColumn(name = "usuario_tipo_pessoa", referencedColumnName = "tipo_pessoa")
    @JoinColumn(name = "usuario_tipo", referencedColumnName = "tipo")
    private UsuarioEntity usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<MeioPagamentoEntity> meiosPagamento = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<AnexoEntity> anexos = new ArrayList<>();

}
