package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.NivelEntity;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryNivel;
import br.com.farmadelivery.factory.FactoryNivelEntity;
import br.com.farmadelivery.repository.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NivelService {

    @Autowired
    private NivelRepository nivelRepository;

    @Lazy
    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private FactoryNivelEntity factoryNivelEntity;

    @Autowired
    private FactoryNivel factoryNivel;

    public Optional<NivelEntity> consulta(Long id) {
        return nivelRepository.findById(id);
    }

    public List<Nivel> consultaPorFarmacia(Long farmaciaDocumento) {
        List<Nivel> niveis = new ArrayList<>();
        List<NivelEntity> nivelEntities = nivelRepository.findByFarmaciaDocumento(farmaciaDocumento);
        nivelEntities.forEach(nivelEntity -> {
            niveis.add(factoryNivel.build(nivelEntity));
        });
        return niveis;
    }

    @Transactional
    public NivelEntity cadastra(Long farmaciaDocumento, Nivel nivel) {
        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não existe");

        NivelEntity nivelEntity = factoryNivelEntity.build(nivel, optionalFarmacia.get());
        return nivelRepository.save(nivelEntity);
    }

    @Transactional
    public void altera(Long id, Nivel nivel) {
        Optional<NivelEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("nível não existe");

        NivelEntity entity = optional.get();
        entity.setNome(nivel.getNome());
        entity.setDescricao(nivel.getDescricao());
        nivelRepository.save(entity);
    }

}
