package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.domain.Secao;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.NivelEntity;
import br.com.farmadelivery.entity.SecaoEntity;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactorySecao;
import br.com.farmadelivery.factory.FactorySecaoEntity;
import br.com.farmadelivery.repository.SecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecaoService {

    @Autowired
    private SecaoRepository secaoRepository;

    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private FactorySecaoEntity factorySecaoEntity;

    @Autowired
    private FactorySecao factorySecao;

    public Optional<SecaoEntity> consulta(Long id) {
        return secaoRepository.findById(id);
    }

    public List<Secao> consultaPorFarmacia(Long farmaciaDocumento) {
        List<Secao> secoes = new ArrayList<>();
        List<SecaoEntity> secaoEntities = secaoRepository.findByFarmaciaDocumento(farmaciaDocumento);
        secaoEntities.forEach(secaoEntity -> {
            secoes.add(factorySecao.build(secaoEntity));
        });
        return secoes;
    }

    @Transactional
    public SecaoEntity cadastra(Long farmaciaDocumento, Secao secao) {
        Optional<FarmaciaEntity> optional = farmaciaService.consulta(farmaciaDocumento);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não existe");

        SecaoEntity secaoEntity = factorySecaoEntity.build(secao);
        secaoEntity.setFarmacia(optional.get());
        return secaoRepository.save(secaoEntity);
    }

    @Transactional
    public void altera(Long id, Secao secao) {
        Optional<SecaoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("seção não existe");

        SecaoEntity entity = optional.get();
        entity.setNome(secao.getNome());
        entity.setDescricao(secao.getDescricao());
        secaoRepository.save(entity);
    }

}
