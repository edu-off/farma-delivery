package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.entity.EntregaEntity;
import br.com.farmadelivery.entity.EntregadorEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.StatusEntregaEnum;
import br.com.farmadelivery.enums.StatusPagamentoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryEntregaEntity;
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

    public Optional<EntregaEntity> consulta(Long id) {
        return entregaRepository.findById(id);
    }

    @Transactional
    public void cadastra(Entrega entrega) {
        Optional<PedidoEntity> optionalPedido = pedidoService.consulta(entrega.getPedidoId());
        if (optionalPedido.isEmpty())
            throw new EntidadeNaoEncontradaException("pedido não encontrado");

        EntregadorEntity entregadorEntity = entregadorService.consultaEntregadorDispoinivel();
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
