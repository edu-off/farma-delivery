package br.com.farmadelivery.exception.tratamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable {

    private String timestamp;
    private Integer status;
    private String mensagem;
    private String caminho;

}