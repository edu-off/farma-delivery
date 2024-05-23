package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Secao;
import br.com.farmadelivery.service.SecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secao")
public class SecaoController {

    @Autowired
    private SecaoService secaoService;

    @GetMapping("/consulta/{farmaciaDocumento}")
    private ResponseEntity<List<Secao>> consultaPorFarmaciaDocumento(@PathVariable Long farmaciaDocumento) {
        return ResponseEntity.ok(secaoService.consultaPorFarmacia(farmaciaDocumento));
    }

    @PostMapping("/cadastra/{farmaciaDocumento}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @RequestBody Secao secao) {
        secaoService.cadastra(farmaciaDocumento, secao);
        return ResponseEntity.ok("seção cadastrada com sucesso");
    }

    @PostMapping("/altera/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @RequestBody Secao secao) {
        secaoService.altera(id, secao);
        return ResponseEntity.ok("seção alterada com sucesso");
    }

}
