package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.MeioPagamentoCartaoCredito;
import br.com.farmadelivery.service.MeioPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meio-pagamento")
public class MeioPagamentoController {

    @Autowired
    private MeioPagamentoService meioPagamentoService;

    @GetMapping("/consulta-cartao-credito-por-cliente/{clienteId}")
    private ResponseEntity<List<MeioPagamentoCartaoCredito>> consultaCartaoCreditoPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(meioPagamentoService.consultaPorCliente(clienteId));
    }

    @PostMapping("/cadastra-cartao-credito/{clienteId}")
    private ResponseEntity<String> cadastraCartaoCredito(@PathVariable Long clienteId, @Valid @RequestBody MeioPagamentoCartaoCredito meioPagamentoCartaoCredito) {
        meioPagamentoService.cadastraCartaoCredito(clienteId, meioPagamentoCartaoCredito);
        return ResponseEntity.ok("cartão de crédito cadastrado com sucesso");
    }

    @PostMapping("/altera-cartao-credito/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @Valid @RequestBody MeioPagamentoCartaoCredito meioPagamentoCartaoCredito) {
        meioPagamentoService.alteraCartaoCredito(id, meioPagamentoCartaoCredito);
        return ResponseEntity.ok("cartão de crédito alterado com sucesso");
    }

}
