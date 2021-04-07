package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @PastOrPresent
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Usuario usuario;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Produto produto;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(@NotBlank String titulo,
                    @NotNull @Valid Usuario usuario,
                    @NotNull @Valid Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEmailUsuario() {
        return this.usuario.getUsername();
    }

    public String getNomeProduto() {
        return this.produto.getNome();
    }
}
