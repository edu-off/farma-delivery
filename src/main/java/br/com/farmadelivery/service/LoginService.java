package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Login;
import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.exception.negocio.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;

    public void efetuaLogin(Login login) {
        UsuarioEntity entityUsuario = usuarioService.consultaPorLogin(login.getLogin());
        if (Objects.isNull(entityUsuario))
            throw new LoginException("usuário não encontrado");
        if (!entityUsuario.getSenha().equals(login.getSenha()))
            throw new LoginException("usuário ou senha inválido");
    }

}
