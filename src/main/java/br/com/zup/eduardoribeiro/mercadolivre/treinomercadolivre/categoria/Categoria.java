package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    @Deprecated
    public Categoria() {

    }

    public Categoria(@NotBlank String nome) {
        Assert.hasText(nome, "Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setCategoriaMae(Categoria categoriaMae) {
        Assert.notNull(categoriaMae, "Você está settando uma categoria mãe que não existe?");
        this.categoriaMae = categoriaMae;
    }
}
