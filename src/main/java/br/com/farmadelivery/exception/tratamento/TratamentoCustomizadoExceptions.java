package br.com.farmadelivery.exception.tratamento;

import br.com.farmadelivery.exception.negocio.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class TratamentoCustomizadoExceptions {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> erroInternoServidor(Exception ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> argumentoIlegal(IllegalArgumentException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(PagamentoException.class)
    public final ResponseEntity<ExceptionResponse> pagamentoRecusado(PagamentoException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(exceptionResponse);
    }

    @ExceptionHandler(PedidoException.class)
    public final ResponseEntity<ExceptionResponse> erroNoPedido(PedidoException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(LoginException.class)
    public final ResponseEntity<ExceptionResponse> erroLogin(LoginException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.UNAUTHORIZED.value(), ex.getMensagem(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(EntidadeJaExisteException.class)
    public final ResponseEntity<ExceptionResponse> entidadeJaExiste(EntidadeJaExisteException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), ex.getMensagem(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public final ResponseEntity<ExceptionResponse> entidadeNaoEncontrada(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), ex.getMensagem(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> requisicaoSemCorpo(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), "requisição sem corpo", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponseValidation> dadosIncorretos(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ExceptionResponseValidation exceptionResponseValidation = new ExceptionResponseValidation(getTimestamp(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "há dados incorretos no corpo da requisição", request.getRequestURI());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> exceptionResponseValidation.adicionaErro(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponseValidation);
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

}
