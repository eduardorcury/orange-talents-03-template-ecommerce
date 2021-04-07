package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "opinioes")
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer nota;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Length(max = 500)
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotNull
    @ManyToOne(optional = false)
    @Valid
    private Produto produto;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Deprecated
    public Opiniao() {
    }

    public Opiniao(@NotNull @Min(1) @Max(5) Integer nota,
                   @NotBlank String titulo,
                   @NotBlank @Length(max = 500) String descricao,
                   @NotNull @Valid Produto produto,
                   @NotNull @Valid Usuario usuario) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
