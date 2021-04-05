package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem.ImagemProduto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantidade;

    @NotBlank
    @Length(max = 1000)
    @Column(nullable = false, length = 1000)
    private String descricao;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Categoria categoria;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Usuario usuario;

    @NotNull
    @Size(min = 3)
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @PositiveOrZero Integer quantidade,
                   @NotBlank @Length(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria,
                   @NotNull @Valid Usuario usuario,
                   @NotNull @Size(min = 3) Collection<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.caracteristicas = caracteristicas.stream().map(caracteristica ->
                caracteristica.converterParaModel(this)).collect(Collectors.toSet());
        Assert.isTrue(this.caracteristicas.size() >= 3, "O produto precisa ter no mínimo três características");
    }

    public boolean pertenceAoUsuario(String usuarioLogin) {
        return this.usuario.getUsername().equals(usuarioLogin);
    }

    public void associaImagens(Set<URL> links) {
        this.imagens.addAll(links
                .stream()
                .map(link -> new ImagemProduto(link.toString(), this))
                .collect(Collectors.toSet()));
    }
}
