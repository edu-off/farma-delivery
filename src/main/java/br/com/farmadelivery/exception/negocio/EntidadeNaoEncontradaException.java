package br.com.farmadelivery.exception.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadeNaoEncontradaException extends RuntimeException {

    private String mensagem;

}
