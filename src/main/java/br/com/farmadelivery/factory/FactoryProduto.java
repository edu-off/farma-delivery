package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.*;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.FuncionarioEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactoryProduto implements Factory {

    public Produto buildFromMedicamento(Medicamento medicamento) {
        return Produto.builder()
                .status(medicamento.getStatus())
                .nome(medicamento.getNome())
                .descricao(medicamento.getDescricao())
                .precoUnitario(medicamento.getPrecoUnitario())
                .quantidade(medicamento.getQuantidade())
                .build();
    }

    public Produto buildFromProdutoEntity(ProdutoEntity produtoEntity, List<Long> anexosId) {
        return Produto.builder()
                .status(produtoEntity.getStatus())
                .nome(produtoEntity.getNome())
                .descricao(produtoEntity.getDescricao())
                .precoUnitario(produtoEntity.getPrecoUnitario())
                .quantidade(produtoEntity.getQuantidadeEstoque())
                .anexosId(anexosId)
                .build();
    }

    public Produto buildFromProdutoEntity(ProdutoEntity produtoEntity) {
        return Produto.builder()
                .status(produtoEntity.getStatus())
                .nome(produtoEntity.getNome())
                .descricao(produtoEntity.getDescricao())
                .precoUnitario(produtoEntity.getPrecoUnitario())
                .quantidade(produtoEntity.getQuantidadeEstoque())
                .build();
    }



}
