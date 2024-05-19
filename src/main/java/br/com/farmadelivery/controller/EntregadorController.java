package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @PostMapping("/cadastra")
    private ResponseEntity<String> cadastra(@RequestBody Entregador entregador) {
        entregadorService.cadastra(entregador);
        return ResponseEntity.ok("entregador cadastrado com sucesso");
    }

    @PutMapping("/altera")
    private ResponseEntity<String> altera(@PathVariable Long id, @RequestBody Entregador entregador) {
        entregadorService.altera(entregador);
        return ResponseEntity.ok("entregador alterado com sucesso");
    }

    @PutMapping("/ativa/{id}")
    private ResponseEntity<String> ativa(@PathVariable Long id) {
        entregadorService.ativa(id);
        return ResponseEntity.ok("entregador ativado com sucesso");
    }

    @PutMapping("/inativa/{id}")
    private ResponseEntity<String> inativa(@PathVariable Long id) {
        entregadorService.inativa(id);
        return ResponseEntity.ok("entregador inativado com sucesso");
    }

}
