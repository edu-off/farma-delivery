package br.com.farmadelivery.service;

import br.com.farmadelivery.entity.CartaoCreditoEntity;
import br.com.farmadelivery.entity.MeioPagamentoEntity;
import br.com.farmadelivery.entity.PagamentoEntity;
import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import br.com.farmadelivery.enums.StatusPagamentoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryPagamentoEntity;
import br.com.farmadelivery.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private FactoryPagamentoEntity factoryPagamentoEntity;

    public Optional<PagamentoEntity> consulta(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Transactional
    public Boolean confirmaPagamento(PedidoEntity pedidoEntity, Long meioPagamentoId) {
        AtomicReference<MeioPagamentoEntity> atomicMeioPagamentoEntity = new AtomicReference<>();
        pedidoEntity.getCliente().getMeiosPagamento().forEach(meio -> {
            if (Objects.equals(meio.getId(), meioPagamentoId))
                atomicMeioPagamentoEntity.set(meio);
        });
        MeioPagamentoEntity meioPagamentoEntity = atomicMeioPagamentoEntity.get();

        PagamentoEntity entity = factoryPagamentoEntity.build(StatusPagamentoEnum.PENDENTE, meioPagamentoEntity, pedidoEntity);
        pagamentoRepository.save(entity);
        if (!entity.getMeioPagamento().getMeioPagamento().equals(MeiosPagamentoEnum.CARTAO_CREDITO))
            return true;

        Boolean foiPago = cobraPagamento(pedidoEntity.getPreco(), entity.getMeioPagamento().getCartaoCredito());
        entity.setStatus(foiPago ? StatusPagamentoEnum.PAGO : StatusPagamentoEnum.RECUSADO);
        pagamentoRepository.save(entity);
        return foiPago;
    }

    @Transactional
    public void alteraStatus(Long id, StatusPagamentoEnum status) {
        Optional<PagamentoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("pagamento não encontrado");
        PagamentoEntity entity = optional.get();
        entity.setStatus(status);
        pagamentoRepository.save(entity);
    }


    private Boolean cobraPagamento(Double preco, CartaoCreditoEntity cartaoCreditoEntity) {
        //Implementação de integração do gateway de pagamento
        //Default true para simular sempre o sucesso
        return true;
    }

}
