package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Medicamento;
import br.com.farmadelivery.service.MedicamentoService;
import jakarta.validation.Valid;
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

    @PostMapping("/cadastra/{farmaciaDocumento}/{secaoId}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @PathVariable Long secaoId, @Valid @RequestBody Medicamento medicamento) {
        medicamentoService.cadastra(farmaciaDocumento, secaoId, medicamento);
        return ResponseEntity.ok("medicamento cadastrado com sucesso");
    }

    @PutMapping("/altera/{secaoId}/{id}")
    private ResponseEntity<String> altera(@PathVariable Long secaoId, @PathVariable Long id,@Valid @RequestBody Medicamento medicamento) {
        medicamentoService.altera(id, secaoId, medicamento);
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
