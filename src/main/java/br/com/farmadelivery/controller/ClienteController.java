package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Cliente;
import br.com.farmadelivery.enums.TiposPessoaEnum;
import br.com.farmadelivery.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastra")
    private ResponseEntity<String> cadastra(@RequestBody Cliente client) {
        clienteService.cadastra(client);
        return ResponseEntity.ok("cliente cadastrado com sucesso");
    }

    @PutMapping("/altera/{id}")
    private ResponseEntity<String> altera(@PathVariable Long id, @RequestBody Cliente client) {
        clienteService.altera(id, client);
        return ResponseEntity.ok("cliente alterado com sucesso");
    }

    @PutMapping("/ativa/{id}")
    private ResponseEntity<String> ativa(@PathVariable Long id) {
        clienteService.ativa(id);
        return ResponseEntity.ok("cliente ativado com sucesso");
    }

    @PutMapping("/inativa/{id}")
    private ResponseEntity<String> inativa(@PathVariable Long id) {
        clienteService.inativa(id);
        return ResponseEntity.ok("cliente inativado com sucesso");
    }

}
