package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Entregador;
import br.com.farmadelivery.service.EntregadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @GetMapping("/consulta-todos")
    private ResponseEntity<List<Entregador>> consultaTodos() {
        return ResponseEntity.ok(entregadorService.consultaTodos());
    }

    @PostMapping("/cadastra")
    private ResponseEntity<String> cadastra(@Valid @RequestBody Entregador entregador) {
        entregadorService.cadastra(entregador);
        return ResponseEntity.ok("entregador cadastrado com sucesso");
    }

    @PutMapping("/altera/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @Valid @RequestBody Entregador entregador) {
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
