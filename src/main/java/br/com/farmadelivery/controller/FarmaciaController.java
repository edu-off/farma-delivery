package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Farmacia;
import br.com.farmadelivery.dto.CadastroFarmaciaDto;
import br.com.farmadelivery.service.FarmaciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farmacia")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    @PostMapping("/cadastra")
    private ResponseEntity<String> cadastra(@RequestBody CadastroFarmaciaDto dto) {
        farmaciaService.cadastra(dto);
        return ResponseEntity.ok("conta criada com sucesso");
    }

    @PutMapping("/altera")
    private ResponseEntity<String> altera(@RequestBody Farmacia farmacia) {
        farmaciaService.altera(farmacia);
        return ResponseEntity.ok("conta alterada com sucesso");
    }

    @PutMapping("/ativa/{farmaciaDocumento}")
    private ResponseEntity<String> ativa(@PathVariable Long farmaciaDocumento) {
        farmaciaService.ativa(farmaciaDocumento);
        return ResponseEntity.ok("conta ativada com sucesso");
    }

    @PutMapping("/inativa/{farmaciaDocumento}")
    private ResponseEntity<String> inativa(@PathVariable Long farmaciaDocumento) {
        farmaciaService.inativa(farmaciaDocumento);
        return ResponseEntity.ok("conta inativada com sucesso");
    }

}
