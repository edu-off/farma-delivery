package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.NivelEntity;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryNivelEntity;
import br.com.farmadelivery.repository.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<NivelEntity> consulta(Long id) {
        return nivelRepository.findById(id);
    }

    @Transactional
    public NivelEntity cadastra(Long farmaciaDocumento, Nivel nivel) {
        Optional<FarmaciaEntity> optional = farmaciaService.consulta(farmaciaDocumento);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não existe");

        NivelEntity nivelEntity = factoryNivelEntity.build(nivel);
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
