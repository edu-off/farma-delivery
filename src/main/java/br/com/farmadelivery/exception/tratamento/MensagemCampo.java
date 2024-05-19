package br.com.farmadelivery.exception.tratamento;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensagemCampo implements Serializable {

    private String campoNome;
    private String mensagem;

}