package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Funcionario;
import br.com.farmadelivery.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/consulta/{farmaciaDocumento}/{matricula}")
    private ResponseEntity<Funcionario> consulta(@PathVariable String matricula, @PathVariable Long farmaciaDocumento) {
        return ResponseEntity.ok(funcionarioService.consulta(matricula, farmaciaDocumento));
    }

    @GetMapping("/consulta/{farmaciaDocumento}")
    private ResponseEntity<List<Funcionario>> consultaTodos(@PathVariable Long farmaciaDocumento) {
        return ResponseEntity.ok(funcionarioService.consultaTodosPorFarmacia(farmaciaDocumento));
    }

    @PostMapping("/cadastra/{farmaciaDocumento}/{nivelId}")
    private ResponseEntity<String> cadastra(@PathVariable Long farmaciaDocumento, @PathVariable Long nivelId, @Valid @RequestBody Funcionario funcionario) {
        funcionarioService.cadastra(farmaciaDocumento, nivelId, funcionario);
        return ResponseEntity.ok("funcionário cadastrado com sucesso");
    }

    @PutMapping("/altera/{farmaciaDocumento}/{nivelId}")
    private ResponseEntity<String> altera(@PathVariable Long farmaciaDocumento, @PathVariable Long nivelId, @Valid @RequestBody Funcionario funcionario) {
        funcionarioService.altera(farmaciaDocumento, nivelId, funcionario);
        return ResponseEntity.ok("funcionário alterado com sucesso");
    }

    @PutMapping("/ativa/{farmaciaDocumento}/{matricula}")
    private ResponseEntity<String> ativa(@PathVariable Long farmaciaDocumento, @PathVariable String matricula) {
        funcionarioService.ativa(farmaciaDocumento,matricula);
        return ResponseEntity.ok("funcionário ativado com sucesso");
    }

    @PutMapping("/inativa/{farmaciaDocumento}/{matricula}")
    private ResponseEntity<String> inativa(@PathVariable Long farmaciaDocumento, @PathVariable String matricula) {
        funcionarioService.inativa(farmaciaDocumento,matricula);
        return ResponseEntity.ok("funcionário inativado com sucesso");
    }

}
