package br.com.farmadelivery.controller;

import br.com.farmadelivery.domain.Login;
import br.com.farmadelivery.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
