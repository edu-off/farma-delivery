package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.dto.ProdutoComAnexoDto;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.entity.NivelEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryFuncionarioEntity;
import br.com.farmadelivery.factory.FactoryMedicamentoEntity;
import br.com.farmadelivery.factory.FactoryProduto;
import br.com.farmadelivery.factory.FactoryProdutoEntity;
import br.com.farmadelivery.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private NivelService nivelService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FactoryProduto factoryProduto;

    @Autowired
    private FactoryMedicamentoEntity factoryMedicamentoEntity;
    @Autowired
    private FactoryProdutoEntity factoryProdutoEntity;

    public Optional<MedicamentoEntity> consulta(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Transactional
    public void cadastra(Long farmaciaDocumento, Long nivelId, Medicamento medicamento) {
        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        Optional<NivelEntity> optionalNivel = nivelService.consulta(nivelId);
        if (optionalNivel.isEmpty())
            throw new EntidadeNaoEncontradaException("nível não encontrado");

        Produto produto = factoryProduto.buildFromMedicamento(medicamento);
        ProdutoEntity produtoEntity = produtoService.cadastra(farmaciaDocumento, nivelId, produto);
        MedicamentoEntity medicineEntity = factoryMedicamentoEntity.buildFromMedicamentoAndProduto(medicamento, produtoEntity);
        medicamentoRepository.save(medicineEntity);
    }

    @Transactional
    public void altera(Long id, Long nivelId, Medicamento medicamento) {
        Optional<MedicamentoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("medicamento não encontrado");

        Optional<NivelEntity> optionalNivel = nivelService.consulta(nivelId);
        if (optionalNivel.isEmpty())
            throw new EntidadeNaoEncontradaException("nível não encontrado");

        Produto produto = factoryProduto.buildFromMedicamento(medicamento);
        MedicamentoEntity entity = optional.get();
        entity.setDosagem(medicamento.getDosagem());
        entity.setLaboratorio(medicamento.getLaboratorio());
        entity.setRequerReceitaMedica(medicamento.getRequerReceitaMedica());
        medicamentoRepository.save(entity);
        produtoService.altera(entity.getProduto().getId(), nivelId, produto);
    }

    @Transactional
    public void ativa(Long id) {
        Optional<MedicamentoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("medicamento não encontrado");

        MedicamentoEntity entity = optional.get();
        if (entity.getProduto().getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("medicamento já ativo");

        entity.getProduto().setStatus(StatusAtivacaoEnum.ATIVO);
        medicamentoRepository.save(entity);
    }

    @Transactional
    public void inativa(Long id) {
        Optional<MedicamentoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("medicamento não encontrado");

        MedicamentoEntity entity = optional.get();
        if (entity.getProduto().getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("medicamento já inativado");

        entity.getProduto().setStatus(StatusAtivacaoEnum.INATIVADO);
        medicamentoRepository.save(entity);
    }

    public void validaAnexos(Map<Long, ProdutoComAnexoDto> produtos) {
        produtos.forEach((id, produtoComAnexo) -> {
            MedicamentoEntity medicamentoEntity = medicamentoRepository.findByProduto(factoryProdutoEntity.buildFromProduto(produtoComAnexo.getProduto()));
            if (!Objects.isNull(medicamentoEntity)) {
                if (!medicamentoEntity.getRequerReceitaMedica()) {
                    if (Objects.isNull(produtoComAnexo.getAnexo().getRequerReceitaMedica()))
                        throw new IllegalArgumentException("upload da receita médica pendente para o medicamento " + produtoComAnexo.getProduto().getNome());
                    if (Objects.isNull(produtoComAnexo.getAnexo().getDocumentoComFoto()))
                        throw new IllegalArgumentException("upload do documento com foto pendente para o medicamento " + produtoComAnexo.getProduto().getNome());
                }
            }
        });

    }

}
