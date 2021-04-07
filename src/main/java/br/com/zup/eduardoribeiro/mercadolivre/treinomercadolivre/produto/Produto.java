package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.Caracteristica;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem.ImagemProduto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao.Opiniao;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta.Pergunta;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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

    @PastOrPresent
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @NotNull
    @Size(min = 3)
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    private List<Opiniao> opinioes = new ArrayList<>();

    @OneToMany(mappedBy = "produto")
    private List<Pergunta> perguntas = new ArrayList<>();

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

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<ImagemProduto> getImagens() {
        return imagens;
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

    public <R> Set<R> converteImagens(Function<ImagemProduto, R> funcao) {
        return this.imagens
                .stream()
                .map(funcao)
                .collect(Collectors.toSet());
    }

    public <R> Set<R> converteCaracteristica(Function<Caracteristica, R> funcao) {
        return this.caracteristicas
                .stream()
                .map(funcao)
                .collect(Collectors.toSet());
    }

    public <R> List<R> converteOpiniao(Function<Opiniao, R> funcao) {
        return this.opinioes
                .stream()
                .map(funcao)
                .collect(Collectors.toList());
    }

    public <R> List<R> convertePergunta(Function<Pergunta, R> funcao) {
        return this.perguntas
                .stream()
                .map(funcao)
                .collect(Collectors.toList());
    }

    public Double calculaMediaNotas() {
        return this.opinioes
                .stream()
                .map(Opiniao::getNota)
                .mapToInt(value -> value)
                .average()
                .orElse(0.0);
    }

    public Integer retornaTotalDeNotas() {
        return this.opinioes.size();
    }

}
