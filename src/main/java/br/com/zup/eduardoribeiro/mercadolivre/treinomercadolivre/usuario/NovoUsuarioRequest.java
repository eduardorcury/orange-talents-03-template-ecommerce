package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation.CampoUnico;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovoUsuarioRequest {

    @Email
    @NotBlank
    @CampoUnico(campo = "login", classe = Usuario.class)
    private String login;

    @NotBlank
    @Length(min = 6)
    private String senha;

    public NovoUsuarioRequest(@Email @NotBlank String login,
                              @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario converterParaModel() {
        return new Usuario(this.login, this.senha);
    }

}
