package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.domain.MeioPagamentoCartaoCredito;
import br.com.farmadelivery.entity.CartaoCreditoEntity;
import br.com.farmadelivery.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class FactoryCartaoCreditoEntity implements Factory {

    public CartaoCreditoEntity buildFromMeioPagamentoCartaoCredito(MeioPagamentoCartaoCredito cartaoCredito) {
        return CartaoCreditoEntity.builder()
                .nomeCompleto(cartaoCredito.getNomeCompleto())
                .numero(cartaoCredito.getNumero())
                .dataVencimento(cartaoCredito.getDataVencimento())
                .codigoSeguranca(cartaoCredito.getCodigoSeguranca())
                .build();
    }

}
