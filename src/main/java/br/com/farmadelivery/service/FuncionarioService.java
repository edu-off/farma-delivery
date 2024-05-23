package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.*;
import br.com.farmadelivery.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NivelService nivelService;

    @Lazy
    @Autowired
    private FarmaciaService farmaciaService;

    @Autowired
    private FactoryFuncionarioEntityPk factoryFuncionarioEntityPk;

    @Autowired
    private FactoryFuncionarioEntity factoryFuncionarioEntity;

    @Autowired
    private FactoryFuncionario factoryFuncionario;

    @Autowired
    private FactoryUsuario factoryUsuario;
    @Autowired
    private FactoryMedicamento factoryMedicamento;

    public Optional<FuncionarioEntity> consulta(FuncionarioEntityPk pk) {
        return funcionarioRepository.findById(pk);
    }

    public Funcionario consultaDados(String matricula, Long farmaciaDocumento) {
        FuncionarioEntityPk pk = factoryFuncionarioEntityPk.buildFromDadosId(matricula, farmaciaDocumento);
        Optional<FuncionarioEntity> optional = consulta(pk);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("funcionário não encontrado");

        return factoryFuncionario.buildFromFuncionarioEntity(optional.get());
    }

    public List<Funcionario> consultaTodosPorFarmacia(Long farmaciaDocumento) {
        List<Funcionario> funcionarios = new ArrayList<>();
        List<FuncionarioEntity> funcionarioEntities = funcionarioRepository.findByFarmaciaDocumento(farmaciaDocumento);
        funcionarioEntities.forEach(funcionarioEntity -> funcionarios.add(factoryFuncionario.buildFromFuncionarioEntity(funcionarioEntity)));
        return funcionarios;
    }


    @Transactional
    public FuncionarioEntity cadastra(Long farmaciaDocumento, Long idNivel, Funcionario funcionario) {
        Optional<FarmaciaEntity> optionalFarmacia = farmaciaService.consulta(farmaciaDocumento);
        if (optionalFarmacia.isEmpty())
            throw new EntidadeNaoEncontradaException("farmacia não encontrada");

        Optional<NivelEntity> optionalNivel = nivelService.consulta(idNivel);
        if (optionalNivel.isEmpty())
            throw new EntidadeNaoEncontradaException("nivel não encontrado");

        FuncionarioEntityPk pk = factoryFuncionarioEntityPk.buildFromDadosId(funcionario.getMatricula(),farmaciaDocumento);
        if (consulta(pk).isPresent())
            throw new EntidadeJaExisteException("funcionário já existe");

        Usuario usuario = factoryUsuario.buildFromFuncionario(funcionario);
        UsuarioEntity usuarioEntity = usuarioService.cadastra(usuario, TiposUsuarioEnum.FUNCIONARIO);
        FuncionarioEntity entity = factoryFuncionarioEntity.buildFromVariosDados(pk, usuarioEntity, optionalFarmacia.get(), optionalNivel.get());
        return funcionarioRepository.save(entity);
    }

    @Transactional
    public void altera(Long farmaciaDocumento, Long idNivel, Funcionario funcionario) {
        FuncionarioEntityPk pk = factoryFuncionarioEntityPk.buildFromDadosId(funcionario.getMatricula(), farmaciaDocumento);
        Optional<NivelEntity> optionalNivel = nivelService.consulta(idNivel);
        if (optionalNivel.isEmpty())
            throw new EntidadeNaoEncontradaException("nivel não encontrado");

        Optional<FuncionarioEntity> optionalFuncionario = consulta(pk);
        if (optionalFuncionario.isEmpty())
            throw new EntidadeNaoEncontradaException("funcionário não encontrado");

        FuncionarioEntity entity = optionalFuncionario.get();
        if (!Objects.equals(entity.getUsuario().getId().getDocumento(), funcionario.getDocumento()))
            throw new IllegalArgumentException("documento não pode ser alterado");
        if (!entity.getUsuario().getId().getTipoPessoa().equals(funcionario.getTipoPessoa()))
            throw new IllegalArgumentException("tipo de pessoa não pode ser alterado");
        if (!Objects.equals(entity.getNivel().getId(), idNivel)) {
            entity.setNivel(optionalNivel.get());
            funcionarioRepository.save(entity);
        }

        usuarioService.altera(entity.getUsuario().getId(), factoryUsuario.buildFromFuncionario(funcionario));
    }

    @Transactional
    public void ativa(Long farmaciaDocumento, String matricula) {
        FuncionarioEntityPk pk = factoryFuncionarioEntityPk.buildFromDadosId(matricula, farmaciaDocumento);
        Optional<FuncionarioEntity> optionalFuncionario = consulta(pk);
        if (optionalFuncionario.isEmpty())
            throw new EntidadeNaoEncontradaException("funcionário não encontrado");

        FuncionarioEntity entity = optionalFuncionario.get();
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("funcionário já ativo");

        Usuario usuario = factoryUsuario.buildFromFuncionarioEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.ATIVO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
    }

    @Transactional
    public void inativa(Long farmaciaDocumento, String matricula) {
        FuncionarioEntityPk pk = factoryFuncionarioEntityPk.buildFromDadosId(matricula, farmaciaDocumento);
        Optional<FuncionarioEntity> optionalFuncionario = consulta(pk);
        if (optionalFuncionario.isEmpty())
            throw new EntidadeNaoEncontradaException("funcionário não encontrado");

        FuncionarioEntity entity = optionalFuncionario.get();
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("funcionário já inativado");

        Usuario usuario = factoryUsuario.buildFromFuncionarioEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.INATIVADO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
    }

}
