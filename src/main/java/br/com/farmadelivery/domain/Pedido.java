package br.com.farmadelivery.domain;

import br.com.farmadelivery.dto.ProdutoComAnexoDto;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private Double preco;
    private Map<Long, ProdutoComAnexoDto> produtos;
    private MeiosPagamentoEnum meioPagamento;

}
