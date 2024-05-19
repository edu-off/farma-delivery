package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.entity.UsuarioEntityPk;
import br.com.farmadelivery.enums.StatusAtivacaoEnum;
import br.com.farmadelivery.enums.TiposPessoaEnum;
import br.com.farmadelivery.enums.TiposUsuarioEnum;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryUsuarioEntity;
import br.com.farmadelivery.factory.FactoryUsuarioEntityPk;
import br.com.farmadelivery.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FactoryUsuarioEntityPk factoryUsuarioEntityPk;

    @Autowired
    private FactoryUsuarioEntity factoryUsuario;

    public UsuarioEntity consultaPorLogin(String login) {
        return usuarioRepository.findByEmail(login);
    }

    public Optional<UsuarioEntity> consulta(TiposPessoaEnum tipoPessoa, Long documento, TiposUsuarioEnum tipoUsuario) {
        UsuarioEntityPk pk = factoryUsuarioEntityPk.buildFromDadosId(tipoPessoa, documento, tipoUsuario);
        return usuarioRepository.findById(pk);
    }

    @Transactional
    public UsuarioEntity cadastra(Usuario usuario, TiposUsuarioEnum tipoUsuario) {
        UsuarioEntityPk pk = factoryUsuarioEntityPk.buildFromDadosId(usuario.getTipoPessoa(), usuario.getDocumento(), tipoUsuario);
        if (usuarioRepository.findById(pk).isPresent())
            throw new EntidadeJaExisteException("usuário já existe");

        UsuarioEntity entity = factoryUsuario.buildFromUsuarioEntityPkAndUsuario(pk, usuario);
        entity.setStatus(StatusAtivacaoEnum.ATIVO);
        return usuarioRepository.save(entity);
    }

    @Transactional
    public void altera(UsuarioEntityPk pk, Usuario usuario) {
        Optional<UsuarioEntity> optional = consulta(pk.getTipoPessoa(), pk.getDocumento(), pk.getTipo());
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("usuário não encontrado");

        UsuarioEntity entity = optional.get();
        entity.setNome(usuario.getNome());
        entity.setStatus(usuario.getStatus());
        entity.setApelido(usuario.getApelido());
        entity.setEmail(usuario.getEmail());
        entity.setSenha(usuario.getSenha());
        usuarioRepository.save(entity);
    }

}
