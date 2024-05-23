package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.entity.UsuarioEntityPk;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.Factory;
import br.com.farmadelivery.factory.FactoryCliente;
import br.com.farmadelivery.factory.FactoryClienteEntity;
import br.com.farmadelivery.factory.FactoryUsuario;
import br.com.farmadelivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private FactoryUsuario factoryUsuario;

    @Autowired
    private FactoryClienteEntity factoryClienteEntity;

    @Autowired
    private FactoryCliente factoryCliente;

    public Optional<ClienteEntity> consulta(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente consultaDados(Long id) {
        Optional<ClienteEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("cliente não encontrado");

        return factoryCliente.buildFromClienteEntity(optional.get());
    }

    @Transactional
    public void cadastra(Cliente cliente) {
        Optional<UsuarioEntity> optional = usuarioService.consulta(cliente.getTipoPessoa(), cliente.getDocumento(), TiposUsuarioEnum.CLIENTE);
        if (optional.isPresent())
            throw new EntidadeJaExisteException("cliente já existe");

        Usuario usuario = factoryUsuario.buildFromCliente(cliente);
        ClienteEntity entity = factoryClienteEntity.buildFromCliente(cliente);
        entity.setUsuario(usuarioService.cadastra(usuario, TiposUsuarioEnum.CLIENTE));
        entity.setEndereco(enderecoService.cadastra(cliente.getEndereco()));
        clienteRepository.save(entity);
    }

    @Transactional
    public void altera(Long id, Cliente cliente) {
        ClienteEntity entity = getClienteEntityFromOptional(consulta(id));
        if (!Objects.equals(entity.getUsuario().getId().getDocumento(), cliente.getDocumento()))
            throw new IllegalArgumentException("documento não pode ser alterado");
        if (!entity.getUsuario().getId().getTipoPessoa().equals(cliente.getTipoPessoa()))
            throw new IllegalArgumentException("tipo de pessoa não pode ser alterado");

        entity.setDdd(cliente.getDdd());
        entity.setTelefone(cliente.getTelefone());
        clienteRepository.save(entity);
        usuarioService.altera(entity.getUsuario().getId(), factoryUsuario.buildFromCliente(cliente));
        enderecoService.altera(entity.getEndereco().getId(), cliente.getEndereco());
    }

    @Transactional
    public void ativa(Long id) {
        ClienteEntity entity = getClienteEntityFromOptional(consulta(id));
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.ATIVO))
            throw new IllegalArgumentException("cliente já ativo");

        Usuario usuario = factoryUsuario.buildFromClienteEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.ATIVO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
        clienteRepository.save(entity);
    }

    @Transactional
    public void inativa(Long id) {
        ClienteEntity entity = getClienteEntityFromOptional(consulta(id));
        if (entity.getUsuario().getStatus().equals(StatusAtivacaoEnum.INATIVADO))
            throw new IllegalArgumentException("cliente já inativado");

        Usuario usuario = factoryUsuario.buildFromClienteEntity(entity);
        usuario.setStatus(StatusAtivacaoEnum.INATIVADO);
        usuarioService.altera(entity.getUsuario().getId(), usuario);
        clienteRepository.save(entity);
    }

    private ClienteEntity getClienteEntityFromOptional(Optional<ClienteEntity> optional) {
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("cliente não encontrado");
        return optional.get();
    }

}
