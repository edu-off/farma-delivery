package br.com.farmadelivery.entity;

import br.com.farmadelivery.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamento")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;

    @ManyToOne
    @JoinColumn(name = "meio_pagamento_id", referencedColumnName = "id")
    private MeioPagamentoEntity meioPagamento;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

}
