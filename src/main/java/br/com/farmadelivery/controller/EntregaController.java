package br.com.farmadelivery.controller;

import br.com.farmadelivery.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PutMapping("/inicia/{id}")
    private ResponseEntity<String> inicia(@PathVariable Long id) {
        entregaService.inicia(id);
        return ResponseEntity.ok("entrega iniciada com sucesso");
    }

    @PutMapping("/finaliza/{id}")
    private ResponseEntity<String> finaliza(@PathVariable Long id) {
        entregaService.finaliza(id);
        return ResponseEntity.ok("entrega finalizada com sucesso");
    }

}
