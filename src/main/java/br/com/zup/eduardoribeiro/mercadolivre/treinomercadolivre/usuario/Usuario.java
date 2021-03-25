package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @Email
    @NotBlank
    @Column(nullable = false)
    private String login;

    @NotBlank
    @Length(min = 6)
    @Column(nullable = false)
    private String senha;

    @Deprecated
    public Usuario() {

    }

    public Usuario(@Email @NotBlank String login,
                   @NotBlank @Length(min = 6) String senha) {

        Assert.hasLength(login, "Login não pode estar em branco");
        Assert.hasLength(login, "Senha não pode estar em branco");
        Assert.isTrue(senha.length() >= 6, "Senha deve possuir no mínimo 6 caracteres");
        Assert.isTrue(isPlainText(senha), "Senha possivelmente já está encodada");

        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);

    }

    private boolean isPlainText(String senha) {

        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
        return !BCRYPT_PATTERN.matcher(senha).matches();

    }

}
