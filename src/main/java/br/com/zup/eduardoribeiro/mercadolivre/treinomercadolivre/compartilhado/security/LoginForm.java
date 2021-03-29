package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.security;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginForm {

    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Length(min = 6)
    private String senha;

    public LoginForm(@Email @NotBlank String login,
                     @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        Assert.hasText(login, "Login não pode estar em branco");
        Assert.hasText(senha, "Senha não pode estar em branco");
        return new UsernamePasswordAuthenticationToken(this.login, this.senha);
    }
}
