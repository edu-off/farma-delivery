package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusAtivacaoEnum;
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
@Table(name = "farmacia")
public class FarmaciaEntity {

    @Id
    private Long documento;

    @Enumerated(EnumType.STRING)
    private StatusAtivacaoEnum status;

    @Column(name = "razao_social")
    private String razaoSocial;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "farmacia", fetch = FetchType.LAZY)
    private List<ProdutoEntity> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "farmacia", fetch = FetchType.LAZY)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "farmacia", fetch = FetchType.LAZY)
    private List<FuncionarioEntity> funcionarios = new ArrayList<>();

    @OneToMany(mappedBy = "farmacia", fetch = FetchType.LAZY)
    private List<NivelEntity> niveis = new ArrayList<>();

    @OneToMany(mappedBy = "farmacia", fetch = FetchType.LAZY)
    private List<SecaoEntity> secoes = new ArrayList<>();

}
