package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Login;
import br.com.farmadelivery.domain.Secao;
import br.com.farmadelivery.service.LoginService;
import br.com.farmadelivery.service.SecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    private ResponseEntity<String> login(@RequestBody Login login) {
        loginService.efetuaLogin(login);
        return ResponseEntity.ok("login efetuado com sucesso");
    }

}
