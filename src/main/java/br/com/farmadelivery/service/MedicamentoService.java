package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryMedicamento;
import br.com.farmadelivery.factory.FactoryMedicamentoEntity;
import br.com.farmadelivery.factory.FactoryProduto;
import br.com.farmadelivery.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private SecaoService secaoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FactoryProduto factoryProduto;

    @Autowired
    private FactoryMedicamentoEntity factoryMedicamentoEntity;

    @Autowired
    private FactoryMedicamento factoryMedicamento;

    public Optional<MedicamentoEntity> consulta(Long id) {
        return medicamentoRepository.findById(id);
    }

    public MedicamentoEntity consultaPorProdutoId(Long produtoId) {
        return medicamentoRepository.findByProdutoId(produtoId);
    }

    public List<Medicamento> consultaPorFarmaciaDocumento(Long farmaciaDocumento) {
        List<Medicamento> medicamentos = new ArrayList<>();
        List<MedicamentoEntity> medicamentoEntities = medicamentoRepository.findByFarmaciaDocumento(farmaciaDocumento);
        medicamentoEntities.forEach(medicamentoEntity -> {
            ProdutoEntity produtoEntity = medicamentoEntity.getProduto();
            Medicamento medicamento = factoryMedicamento.buildFromMedicamentoEntityAndProdutoEntity(medicamentoEntity, produtoEntity);
            medicamentos.add(medicamento);
        });
        return medicamentos;
    }

    @Transactional
    public void cadastra(Long farmaciaDocumento, Long secaoId, Medicamento medicamento) {
        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        Optional<SecaoEntity> optionalSecao = secaoService.consulta(secaoId);
        if (optionalSecao.isEmpty())
            throw new EntidadeNaoEncontradaException("seção não encontrado");

        Produto produto = factoryProduto.buildFromMedicamento(medicamento);
        ProdutoEntity produtoEntity = produtoService.cadastra(farmaciaDocumento, secaoId, produto);
        MedicamentoEntity medicineEntity = factoryMedicamentoEntity.buildFromMedicamentoAndProduto(medicamento, produtoEntity);
        medicamentoRepository.save(medicineEntity);
    }

    @Transactional
    public void altera(Long id, Long secaoId, Medicamento medicamento) {
        Optional<MedicamentoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("medicamento não encontrado");

        Optional<SecaoEntity> optionalSecao = secaoService.consulta(secaoId);
        if (optionalSecao.isEmpty())
            throw new EntidadeNaoEncontradaException("seção não encontrado");

        Produto produto = factoryProduto.buildFromMedicamento(medicamento);
        MedicamentoEntity entity = optional.get();
        entity.setDosagem(medicamento.getDosagem());
        entity.setLaboratorio(medicamento.getLaboratorio());
        entity.setRequerReceitaMedica(medicamento.getRequerReceitaMedica());
        medicamentoRepository.save(entity);
        produtoService.altera(entity.getProduto().getId(), secaoId, produto);
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

}
