package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Nivel;
import br.com.farmadelivery.service.NivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nivel")
public class NivelController {

    @Autowired
    private NivelService nivelService;

    @PostMapping("/cadastra/{farmaciaDocumento}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @RequestBody Nivel nivel) {
        nivelService.cadastra(farmaciaDocumento, nivel);
        return ResponseEntity.ok("nível cadastrado com sucesso");
    }

    @PostMapping("/altera/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @RequestBody Nivel nivel) {
        nivelService.altera(id, nivel);
        return ResponseEntity.ok("nível alterado com sucesso");
    }

}
