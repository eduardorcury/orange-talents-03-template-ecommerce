package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
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

        Assert.hasText(login, "Login não pode estar em branco");
        Assert.hasText(senha, "Senha não pode estar em branco");
        Assert.isTrue(senha.length() >= 6, "Senha deve possuir no mínimo 6 caracteres");
        Assert.isTrue(isPlainText(senha), "Senha possivelmente já está encodada");

        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);

    }

    private boolean isPlainText(String senha) {

        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
        return !BCRYPT_PATTERN.matcher(senha).matches();

    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        Assert.hasText(this.senha, "Senha não pode estar em branco");
        return this.senha;
    }

    @Override
    public String getUsername() {
        Assert.hasText(this.login, "Login não pode estar em branco");
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id) && dataDeCriacao.equals(usuario.dataDeCriacao) && login.equals(usuario.login) && senha.equals(usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataDeCriacao, login, senha);
    }
}
