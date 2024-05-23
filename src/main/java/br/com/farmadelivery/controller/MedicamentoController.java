package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamento")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/consulta/{farmaciaDocumento}")
    private ResponseEntity<List<Medicamento>> consultaPorFarmaciaDocumento(@PathVariable Long farmaciaDocumento) {
        return ResponseEntity.ok(medicamentoService.consultaPorFarmaciaDocumento(farmaciaDocumento));
    }

    @PostMapping("/cadastra/{farmaciaDocumento}/{idNivel}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @PathVariable Long idNivel, @RequestBody Medicamento medicamento) {
        medicamentoService.cadastra(farmaciaDocumento, idNivel, medicamento);
        return ResponseEntity.ok("medicamento cadastrado com sucesso");
    }

    @PutMapping("/altera/{id}/{idNivel}")
    private ResponseEntity<String> altera(@PathVariable Long id, @PathVariable Long idNivel, @RequestBody Medicamento medicamento) {
        medicamentoService.altera(id, idNivel, medicamento);
        return ResponseEntity.ok("medicamento alterado com sucesso");
    }

    @PutMapping("/ativa/{id}")
    private ResponseEntity<String> ativa(@PathVariable Long id) {
        medicamentoService.ativa(id);
        return ResponseEntity.ok("medicamento ativado com sucesso");
    }

    @PutMapping("/inativa/{id}")
    private ResponseEntity<String> inativa(@PathVariable Long id) {
        medicamentoService.inativa(id);
        return ResponseEntity.ok("medicamento inativado com sucesso");
    }

}
