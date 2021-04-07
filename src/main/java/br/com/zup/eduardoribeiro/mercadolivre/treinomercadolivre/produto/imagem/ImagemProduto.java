package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @NotBlank
    @Column(nullable = false)
    private String link;

    @ManyToOne(optional = false)
    @Valid
    private Produto produto;

    @Deprecated
    public ImagemProduto() {

    }

    public ImagemProduto(@URL @NotBlank String link, @Valid Produto produto) {
        this.link = link;
        this.produto = produto;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagemProduto that = (ImagemProduto) o;
        return link.equals(that.link) && produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, produto);
    }
}
