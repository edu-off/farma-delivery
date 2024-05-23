package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Pedido;
import br.com.farmadelivery.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/consulta-pedidos-pendentes-validacao")
    private ResponseEntity<List<Pedido>> consultaPedidosPendentesValidacao() {
        return ResponseEntity.ok(pedidoService.consultaPendentesDeValidacao());
    }

    @PostMapping("/inicia/{farmaciaDocumento}/{clienteId}")
    private ResponseEntity<String> inicia(@PathVariable Long farmaciaDocumento, @PathVariable Long clienteId, @RequestBody Pedido pedido) {
        pedidoService.inicia(clienteId, farmaciaDocumento, pedido);
        return ResponseEntity.ok("pedido inicidado com sucesso");
    }

    @PutMapping("/revisa/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedidoService.revisa(id, pedido);
        return ResponseEntity.ok("pedido revisado com sucesso");
    }

    @PutMapping("/conclui/{id}")
    private ResponseEntity<String> conclui(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedidoService.conclui(id, pedido);
        return ResponseEntity.ok("pedido concluído com sucesso");
    }

    @DeleteMapping("/remove/{id}")
    private ResponseEntity<String> remove(@PathVariable Long id) {
        pedidoService.remove(id);
        return ResponseEntity.ok("pedido removido com sucesso");
    }

    @PutMapping("/encerra/{id}")
    private ResponseEntity<String> encerra(@PathVariable Long id) {
        pedidoService.encerra(id);
        return ResponseEntity.ok("pedido encerrado com sucesso");
    }

    @PutMapping("/cancela/{id}")
    private ResponseEntity<String> cancela(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedidoService.cancela(id, pedido);
        return ResponseEntity.ok("pedido cancelado com sucesso");
    }

    @PutMapping("/valida/{id}/{meioPagamentoId}")
    private ResponseEntity<String> valida(@PathVariable Long id, @PathVariable Long meioPagamentoId, @RequestBody Pedido pedido) {
        pedidoService.valida(id, meioPagamentoId, pedido);
        return ResponseEntity.ok("pedido validado com sucesso");
    }

    @PutMapping("/invalida/{id}")
    private ResponseEntity<String> invalida(@PathVariable Long id) {
        pedidoService.invalida(id);
        return ResponseEntity.ok("pedido invalidado com sucesso");
    }

}
