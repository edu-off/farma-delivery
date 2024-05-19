package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.entity.SecaoEntity;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryProdutoEntity;
import br.com.farmadelivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private SecaoService secaoService;

    @Autowired
    private FactoryProdutoEntity factoryProdutoEntity;

    public Optional<ProdutoEntity> consulta(Long id) {
        return produtoRepository.findById(id);
    }

    public List<ProdutoEntity> retornaListaPorIds(List<Long> ids) {
        return produtoRepository.findAllById(ids);
    }

    @Transactional
    public List<ProdutoEntity> salvaLista(List<ProdutoEntity> produtos) {
        return produtoRepository.saveAll(produtos);
    }

    @Transactional
    public ProdutoEntity cadastra(Long farmaciaDocumento, Long secaoId, Produto produto) {
        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        Optional<SecaoEntity> optionalSecao = secaoService.consulta(secaoId);
        if (optionalSecao.isEmpty())
            throw new EntidadeNaoEncontradaException("seção não encontrada");

        ProdutoEntity entity = factoryProdutoEntity.buildFromProduto(produto);
        entity.setFarmacia(optionalFarmacia.get());
        entity.setSecao(optionalSecao.get());
        return produtoRepository.save(entity);
    }

    @Transactional
    public void altera(Long id, Long secaoId, Produto produto) {
        Optional<ProdutoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("produto não encontrado");

        Optional<SecaoEntity> optionalSecao = secaoService.consulta(secaoId);
        if (optionalSecao.isEmpty())
            throw new EntidadeNaoEncontradaException("seção não encontrada");

        ProdutoEntity entity = optional.get();
        entity.setStatus(produto.getStatus());
        entity.setNome(produto.getNome());
        entity.setDescricao(produto.getDescricao());
        entity.setPrecoUnitario(produto.getPrecoUnitario());
        entity.setQuantidadeEstoque(produto.getQuantidade());
        entity.setSecao(optionalSecao.get());
        produtoRepository.save(entity);
    }

    @Transactional
    public void ativa(Long id) {
        Optional<ProdutoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("produto não encontrado");

        ProdutoEntity entity = optional.get();
        if (entity.getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("produto já ativo");

        entity.setStatus(StatusAtivacaoEnum.ATIVO);
        produtoRepository.save(entity);
    }

    @Transactional
    public void inativa(Long id) {
        Optional<ProdutoEntity> optional = produtoRepository.findById(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("produto não encontrado");

        ProdutoEntity entity = optional.get();
        if (entity.getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("produto já inativado");

        entity.setStatus(StatusAtivacaoEnum.INATIVADO);
        produtoRepository.save(entity);
    }

}
