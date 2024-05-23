package br.com.farmadelivery.domain;

import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Double preco;
    private MeiosPagamentoEnum meioPagamento;
    private Map<Long, Produto> produtos;

}
