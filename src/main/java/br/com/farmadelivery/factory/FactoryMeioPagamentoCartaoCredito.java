package br.com.farmadelivery.factory;

import br.com.farmadelivery.domain.MeioPagamentoCartaoCredito;
import br.com.farmadelivery.entity.CartaoCreditoEntity;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryMeioPagamentoCartaoCredito implements Factory {

    public MeioPagamentoCartaoCredito buildFromCartaoCreditoEntity(CartaoCreditoEntity cartaoCreditoEntity) {
        return MeioPagamentoCartaoCredito.builder()
                .id(cartaoCreditoEntity.getId())
                .meioPagamento(MeiosPagamentoEnum.CARTAO_CREDITO)
                .nomeCompleto(cartaoCreditoEntity.getNomeCompleto())
                .numero(cartaoCreditoEntity.getNumero())
                .dataVencimento(cartaoCreditoEntity.getDataVencimento())
                .codigoSeguranca(cartaoCreditoEntity.getCodigoSeguranca())
                .build();
    }

}
