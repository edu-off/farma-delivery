package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Farmacia;
import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.dto.CadastroFarmaciaDto;
import br.com.farmadelivery.entity.FarmaciaEntity;
import br.com.farmadelivery.entity.NivelEntity;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryFarmacia;
import br.com.farmadelivery.factory.FactoryNivel;
import br.com.farmadelivery.repository.FarmaciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FarmaciaService {

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Lazy
    @Autowired
    private FuncionarioService funcionarioService;

    @Lazy
    @Autowired
    private NivelService nivelService;

    @Autowired
    private FactoryFarmacia factoryFarmacia;

    @Autowired
    private FactoryNivel factoryNivel;

    public Optional<FarmaciaEntity> consulta(Long documento) {
        return farmaciaRepository.findById(documento);
    }

    @Transactional
    public void cadastra(CadastroFarmaciaDto dto) {
        if (consulta(dto.getFarmacia().getDocumento()).isPresent())
            throw new EntidadeJaExisteException("farmacia ja existe");

        FarmaciaEntity entity = factoryFarmacia.buildFromDto(dto);
        entity.setStatus(StatusAtivacaoEnum.ATIVO);
        entity.setEndereco(enderecoService.cadastra(dto.getFarmacia().getEndereco()));
        farmaciaRepository.save(entity);

        Nivel nivel = factoryNivel.build("administrador", "funcionário de nível administrador que possui todas as permissões");
        NivelEntity nivelEntity = nivelService.cadastra(dto.getFarmacia().getDocumento(), nivel);
        funcionarioService.cadastra(dto.getFarmacia().getDocumento(), nivelEntity.getId(), dto.getFuncionario());
    }

    @Transactional
    public void altera(Farmacia farmacia) {
        Optional<FarmaciaEntity> optional = consulta(farmacia.getDocumento());
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        FarmaciaEntity entity = optional.get();
        entity.setRazaoSocial(farmacia.getRazaoSocial());
        farmaciaRepository.save(entity);
        enderecoService.altera(optional.get().getEndereco().getId(), farmacia.getEndereco());
    }

    @Transactional
    public void ativa(Long farmaciaDocumento) {
        Optional<FarmaciaEntity> optional = consulta(farmaciaDocumento);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        FarmaciaEntity entity = optional.get();
        if (entity.getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("farmacia já ativa");

        entity.setStatus(StatusAtivacaoEnum.ATIVO);
        farmaciaRepository.save(entity);
    }

    @Transactional
    public void inativa(Long farmaciaDocumento) {
        Optional<FarmaciaEntity> optional = consulta(farmaciaDocumento);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("farmácia não encontrada");

        FarmaciaEntity entity = optional.get();
        if (entity.getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("farmacia já inativada");

        entity.setStatus(StatusAtivacaoEnum.INATIVADO);
        farmaciaRepository.save(entity);
    }

}
