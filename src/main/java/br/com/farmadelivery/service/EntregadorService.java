package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryEntregador;
import br.com.farmadelivery.factory.FactoryEntregadorEntity;
import br.com.farmadelivery.factory.FactoryUsuario;
import br.com.farmadelivery.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FactoryUsuario factoryUsuario;

    @Autowired
    private FactoryEntregador factoryEntregador;

    @Autowired
    private FactoryEntregadorEntity factoryEntregadorEntity;

    public Optional<EntregadorEntity> consulta(Long id) {
        return entregadorRepository.findById(id);
    }

    public EntregadorEntity consultaEntregadorDispoinivel() {
        return entregadorRepository.findFirstByEstaAlocadoIsFalse();
    }

    public List<Entregador> consultaTodos() {
        List<Entregador> entregadores = new ArrayList<>();
        List<EntregadorEntity> entregadorEntities = entregadorRepository.findAll();
        entregadorEntities.forEach(entregadorEntity -> {
            UsuarioEntity usuarioEntity = entregadorEntity.getUsuario();
            entregadores.add(factoryEntregador.buildFromEntregadorEntity(entregadorEntity, usuarioEntity));
        });
        return entregadores;
    }

    @Transactional
    public EntregadorEntity cadastra(Entregador entregador) {
        Optional<UsuarioEntity> optionalUsuario = usuarioService.consulta(entregador.getTipoPessoa(), entregador.getDocumento(), TiposUsuarioEnum.ENTREGADOR);
        if (optionalUsuario.isPresent())
            throw new EntidadeJaExisteException("usuário para entregador já existe");

        Usuario usuario = factoryUsuario.buildFromEntregador(entregador);
        UsuarioEntity usuarioEntity = usuarioService.cadastra(usuario, TiposUsuarioEnum.ENTREGADOR);
        EntregadorEntity entity = factoryEntregadorEntity.buildEntregador(entregador, usuarioEntity);
        return entregadorRepository.save(entity);
    }

    @Transactional
    public void altera(Entregador entregador) {
        Optional<UsuarioEntity> optionalUsuario = usuarioService.consulta(entregador.getTipoPessoa(), entregador.getDocumento(), TiposUsuarioEnum.ENTREGADOR);
        if (optionalUsuario.isEmpty())
            throw new EntidadeNaoEncontradaException("usuário para entregador não encontrado");

        Optional<EntregadorEntity> optional = consulta(optionalUsuario.get().getEntregador().getId());
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entregador não encontrado");

        EntregadorEntity entity = optional.get();
        if (!Objects.equals(entity.getUsuario().getId().getDocumento(), entregador.getDocumento()))
            throw new IllegalArgumentException("documento não pode ser alterado");
        if (!entity.getUsuario().getId().getTipoPessoa().equals(entregador.getTipoPessoa()))
            throw new IllegalArgumentException("tipo de pessoa não pode ser alterado");

        entity.setEstaAlocado(entregador.isEstaAlocado());
        entregadorRepository.save(entity);
        usuarioService.altera(entity.getUsuario().getId(), factoryUsuario.buildFromEntregador(entregador));
    }

    @Transactional
    public void ativa(Long id) {
        Optional<EntregadorEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entregador não encontrado");

        EntregadorEntity entity = optional.get();
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("entregador já ativo");

        Usuario usuario = factoryUsuario.buildFromEntregadorEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.ATIVO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
    }

    @Transactional
    public void inativa(Long id) {
        Optional<EntregadorEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entregador não encontrado");

        EntregadorEntity entity = optional.get();
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("entregador já inativo");

        Usuario usuario = factoryUsuario.buildFromEntregadorEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.INATIVADO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
    }

}
