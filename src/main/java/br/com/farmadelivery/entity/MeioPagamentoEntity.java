package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
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
@Table(name = "meio_pagamento")
public class MeioPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeiosPagamentoEnum meioPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity cliente;

    @OneToOne(mappedBy = "meioPagamento")
    private CartaoCreditoEntity cartaoCredito;

    @OneToMany(mappedBy = "meioPagamento", fetch = FetchType.LAZY)
    private List<PagamentoEntity> pagamentos = new ArrayList<>();

}
