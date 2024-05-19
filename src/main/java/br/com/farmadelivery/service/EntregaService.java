package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.domain.Usuario;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.*;
import br.com.farmadelivery.exception.negocio.EntidadeJaExisteException;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryClienteEntity;
import br.com.farmadelivery.factory.FactoryEntregaEntity;
import br.com.farmadelivery.factory.FactoryUsuario;
import br.com.farmadelivery.repository.ClienteRepository;
import br.com.farmadelivery.repository.EntregaRepository;
import br.com.farmadelivery.repository.EntregadorRepository;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private FactoryEntregaEntity factoryEntregaEntity;

    public Optional<EntregaEntity> consulta(Long id) {
        return entregaRepository.findById(id);
    }

    @Transactional
    public void cadastra(Entrega entrega) {
        Optional<UsuarioEntity> optionalUsuario = usuarioService.consulta(entrega.getEntregador().getTipoPessoa(), entrega.getEntregador().getDocumento(), TiposUsuarioEnum.ENTREGADOR);
        if (optionalUsuario.isEmpty())
            throw new EntidadeNaoEncontradaException("entregador não encontrado");

        Optional<PedidoEntity> optionalPedido = pedidoService.consulta(entrega.getPedidoId());
        if (optionalPedido.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        EntregaEntity entity = factoryEntregaEntity.buildFromEntrega(StatusEntregaEnum.PENDENTE, entrega,
                optionalPedido.get(), optionalUsuario.get().getEntregador());
        entregaRepository.save(entity);
    }

    @Transactional
    public void inicia(Long id) {
        Optional<EntregaEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entrega não encontrado");

        EntregaEntity entity = optional.get();
        entity.setStatus(StatusEntregaEnum.EM_TRANSITO);
        entregaRepository.save(entity);
    }

    @Transactional
    public void finaliza(Long id, Boolean confirmaPagamento) {
        Optional<EntregaEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entrega não encontrado");

        EntregaEntity entity = optional.get();
        entity.setStatus(StatusEntregaEnum.ENTREGUE);
        entregaRepository.save(entity);

        if (confirmaPagamento)
            pagamentoService.alteraStatus(entity.getPedido().getPagamento().getId(), StatusPagamentoEnum.PAGO);
    }

}
