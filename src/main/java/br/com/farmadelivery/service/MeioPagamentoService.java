package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.MeioPagamentoCartaoCredito;
import br.com.farmadelivery.entity.CartaoCreditoEntity;
import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.MeioPagamentoEntity;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryCartaoCreditoEntity;
import br.com.farmadelivery.factory.FactoryMeioPagamentoCartaoCredito;
import br.com.farmadelivery.factory.FactoryMeioPagamentoEntity;
import br.com.farmadelivery.repository.CartaoCreditoRepository;
import br.com.farmadelivery.repository.MeioPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MeioPagamentoService {

    @Autowired
    private MeioPagamentoRepository meioPagamentoRepository;

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Lazy
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FactoryMeioPagamentoEntity factoryMeioPagamentoEntity;

    @Autowired
    private FactoryCartaoCreditoEntity factoryCartaoCreditoEntity;

    @Autowired
    private FactoryMeioPagamentoCartaoCredito factoryMeioPagamentoCartaoCredito;

    @Transactional
    public List<MeioPagamentoCartaoCredito> consultaPorCliente(Long clienteId) {
        List<MeioPagamentoCartaoCredito> meiosPagamentoCartaoCredito = new ArrayList<>();
        List<MeioPagamentoEntity> meioPagamentoEntities = meioPagamentoRepository.findByClienteId(clienteId);
        meioPagamentoEntities.forEach(meioPagamentoEntity -> {
            if (meioPagamentoEntity.getMeioPagamento().equals(MeiosPagamentoEnum.CARTAO_CREDITO))
                meiosPagamentoCartaoCredito.add(factoryMeioPagamentoCartaoCredito.buildFromCartaoCreditoEntity(meioPagamentoEntity.getCartaoCredito()));
        });
        return meiosPagamentoCartaoCredito;
    }

    @Transactional
    public void cadastraMeioPagamentoDefault(ClienteEntity clienteEntity) {
        MeioPagamentoEntity meioPagamentoEntityDinheiro = factoryMeioPagamentoEntity.buildFromClienteEntity(clienteEntity, MeiosPagamentoEnum.DINHEIRO);
        MeioPagamentoEntity meioPagamentoEntityPix = factoryMeioPagamentoEntity.buildFromClienteEntity(clienteEntity, MeiosPagamentoEnum.PIX);
        meioPagamentoRepository.saveAll(List.of(meioPagamentoEntityDinheiro, meioPagamentoEntityPix));
    }

    @Transactional
    public void cadastraCartaoCredito(Long clienteId, MeioPagamentoCartaoCredito cartaoCredito) {
        Optional<ClienteEntity> optionalCliente = clienteService.consulta(clienteId);
        if (optionalCliente.isEmpty())
            throw new EntidadeNaoEncontradaException("cliente não encontrado");

        MeioPagamentoEntity meioPagamentoEntity = factoryMeioPagamentoEntity.buildFromClienteEntity(optionalCliente.get(), MeiosPagamentoEnum.CARTAO_CREDITO);
        meioPagamentoEntity = meioPagamentoRepository.save(meioPagamentoEntity);
        CartaoCreditoEntity cartaoCreditoEntity = factoryCartaoCreditoEntity.buildFromMeioPagamentoCartaoCredito(cartaoCredito);
        cartaoCreditoEntity.setMeioPagamento(meioPagamentoEntity);
        cartaoCreditoRepository.save(cartaoCreditoEntity);
    }

    @Transactional
    public void alteraCartaoCredito(Long id, MeioPagamentoCartaoCredito cartaoCredito) {
        Optional<MeioPagamentoEntity> optional = meioPagamentoRepository.findById(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("meio de pagamento não encontrado");

        CartaoCreditoEntity cartaoCreditoEntity = cartaoCreditoRepository.findByMeioPagamentoId(optional.get().getId());
        if (Objects.isNull(cartaoCreditoEntity))
            throw new EntidadeNaoEncontradaException("cartão de crédito não encontrado");

        cartaoCreditoEntity.setNomeCompleto(cartaoCredito.getNomeCompleto());
        cartaoCreditoEntity.setNumero(cartaoCredito.getNumero());
        cartaoCreditoEntity.setDataVencimento(cartaoCredito.getDataVencimento());
        cartaoCreditoEntity.setCodigoSeguranca(cartaoCredito.getCodigoSeguranca());
        cartaoCreditoRepository.save(cartaoCreditoEntity);
    }

}
