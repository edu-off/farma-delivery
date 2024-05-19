package br.com.farmadelivery.factory;

import br.com.farmadelivery.entity.ClienteEntity;
import br.com.farmadelivery.entity.MeioPagamentoEntity;
import br.com.farmadelivery.enums.MeiosPagamentoEnum;
import org.springframework.stereotype.Component;

@Component
public class FactoryMeioPagamentoEntity implements Factory {

    public MeioPagamentoEntity buildFromClienteEntity(ClienteEntity clienteEntity, MeiosPagamentoEnum meioPagamento) {
        return MeioPagamentoEntity.builder()
                .meioPagamento(meioPagamento)
                .cliente(clienteEntity)
                .build();
    }

}
