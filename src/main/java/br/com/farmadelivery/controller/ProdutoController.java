package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Produto;
import br.com.farmadelivery.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoServrice;

    @GetMapping("/consulta/{farmaciaDocumento}")
    private ResponseEntity<List<Produto>> consultaTodosPorFarmaciaDocumento(@PathVariable Long farmaciaDocumento) {
        return ResponseEntity.ok(produtoServrice.consultaPorFarmaciaDocumento(farmaciaDocumento));
    }

    @PostMapping("/cadastra/{farmaciaDocumento}/{secaoId}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @PathVariable Long secaoId, @Valid @RequestBody Produto produto) {
        produtoServrice.cadastra(farmaciaDocumento, secaoId, produto);
        return ResponseEntity.ok("produto adicionado com sucesso");
    }

    @PutMapping("/altera/{secaoId}/{id}")
    private ResponseEntity<String> altera(@PathVariable Long secaoId, @PathVariable Long id,  @Valid @RequestBody Produto produto) {
        produtoServrice.altera(id, secaoId, produto);
        return ResponseEntity.ok("produto alterado com sucesso");
    }

    @PutMapping("/ativa/{id}")
    private ResponseEntity<String> ativa(@PathVariable Long id) {
        produtoServrice.ativa(id);
        return ResponseEntity.ok("produto ativado com sucesso");
    }

    @PutMapping("/inativa/{id}")
    private ResponseEntity<String> inativa(@PathVariable Long id) {
        produtoServrice.inativa(id);
        return ResponseEntity.ok("produto inativado com sucesso");
    }

}
