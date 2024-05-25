package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.entity.*;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import br.com.farmadelivery.enums.StatusEntregaEnum;
import br.com.farmadelivery.enums.StatusPagamentoEnum;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.exception.negocio.PedidoException;
import br.com.farmadelivery.factory.FactoryEntregaEntity;
import br.com.farmadelivery.factory.FactoryEntregador;
import br.com.farmadelivery.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregadorService entregadorService;

    @Lazy
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private FactoryEntregaEntity factoryEntregaEntity;
    @Autowired
    private FactoryEntregador factoryEntregador;
    @Autowired
    private UsuarioService usuarioService;

    public Optional<EntregaEntity> consulta(Long id) {
        return entregaRepository.findById(id);
    }

    @Transactional
    public void cadastra(Entrega entrega) {
        Optional<PedidoEntity> optionalPedido = pedidoService.consulta(entrega.getPedidoId());
        if (optionalPedido.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        EntregadorEntity entregadorEntity = entregadorService.consultaEntregadorDisponivel();
        if (Objects.isNull(entregadorEntity))
            throw new EntidadeNaoEncontradaException("nenhum entregador disponível, tente novamente mais tarde");

        EntregaEntity entity = factoryEntregaEntity.buildFromEntrega(StatusEntregaEnum.PENDENTE, entrega,
                optionalPedido.get(), entregadorEntity);
        entregaRepository.save(entity);
    }

    @Transactional
    public void inicia(Long id) {
        Optional<EntregaEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entrega não encontrado");

        EntregaEntity entity = optional.get();
        if (!entity.getStatus().equals(StatusEntregaEnum.PENDENTE))
            throw new PedidoException("início de entrega não permitida para entregas que não estão pendentes");

        entity.setStatus(StatusEntregaEnum.EM_TRANSITO);
        entregaRepository.save(entity);
    }

    @Transactional
    public void finaliza(Long id) {
        Optional<EntregaEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("entrega não encontrado");

        EntregaEntity entity = optional.get();
        if (entity.getStatus().equals(StatusEntregaEnum.PENDENTE))
            throw new PedidoException("finalização de entrega não permitida para entregas pendentes");
        if (entity.getStatus().equals(StatusEntregaEnum.ENTREGUE))
            throw new PedidoException("finalização de entrega não permitida para pedidos entregues");

        entity.setStatus(StatusEntregaEnum.ENTREGUE);
        entregaRepository.save(entity);
        Entregador entregador = factoryEntregador.buildFromEntregadorEntity(entity.getEntregador(), entity.getEntregador().getUsuario());
        entregador.setEstaAlocado(false);
        entregadorService.altera(entregador);
    }

}
