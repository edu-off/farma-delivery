package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryProdutoEntity implements Factory {

    public ProdutoEntity buildFromProduto(Produto produto) {
        return ProdutoEntity.builder()
                .status(produto.getStatus())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .precoUnitario(produto.getPrecoUnitario())
                .quantidadeEstoque(produto.getQuantidade())
                .build();
    }

}
