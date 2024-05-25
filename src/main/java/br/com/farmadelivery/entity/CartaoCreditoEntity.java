package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartao_credito")
public class CartaoCreditoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    private Long numero;

    @Column(name = "data_vencimento")
    private String dataVencimento;

    @Column(name = "codigo_seguranca")
    private Integer codigoSeguranca;

    @OneToOne
    @JoinColumn(name = "meio_pagamento_id", referencedColumnName = "id")
    private MeioPagamentoEntity meioPagamento;

}
