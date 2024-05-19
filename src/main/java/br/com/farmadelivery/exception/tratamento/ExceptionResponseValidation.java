package br.com.farmadelivery.exception.tratamento;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExceptionResponseValidation extends ExceptionResponse {

    private List<MensagemCampo> erros;

    public ExceptionResponseValidation(String timestamp, Integer status, String mensagem, String caminho) {
        super(timestamp, status, mensagem, caminho);
        this.erros = new ArrayList<>();
    }

    public void adicionaErro(String nomeCampo, String mensagem) {
        erros.add(new MensagemCampo(nomeCampo, mensagem));
    }

}