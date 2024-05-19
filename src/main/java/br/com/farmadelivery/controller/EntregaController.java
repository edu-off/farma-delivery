package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Entrega;
import br.com.farmadelivery.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping("/cadastra")
    private ResponseEntity<String> cadastra(@RequestBody Entrega entrega) {
        entregaService.cadastra(entrega);
        return ResponseEntity.ok("entrega cadastra com sucesso");
    }

    @PutMapping("/inicia")
    private ResponseEntity<String> inicia(@PathVariable Long id) {
        entregaService.inicia(id);
        return ResponseEntity.ok("entrega iniciada com sucesso");
    }

    @PutMapping("/finaliza/{id}")
    private ResponseEntity<String> ativa(@PathVariable Long id, @RequestBody Boolean confirmaPagamento) {
        entregaService.finaliza(id, confirmaPagamento);
        return ResponseEntity.ok("entrega finalizada com sucesso");
    }

}
