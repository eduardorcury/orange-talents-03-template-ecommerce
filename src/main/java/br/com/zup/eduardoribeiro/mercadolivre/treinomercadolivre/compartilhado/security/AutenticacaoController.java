package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final AutenticacaoService service;

    public AutenticacaoController(AuthenticationManager manager, AutenticacaoService service) {
        this.manager = manager;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginForm loginForm) {

        UsernamePasswordAuthenticationToken dadosLogin = loginForm.converter();

        try {
            Authentication auth = manager.authenticate(dadosLogin);
            String token = service.gerarToken(auth);
            System.out.println(token);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

}
