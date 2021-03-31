package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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
    @ManyToOne(optional = false)
    private Categoria categoria;

    @NotNull
    @ManyToOne(optional = false)
    private Usuario usuario;

    @NotNull
    @Size(min = 3)
    @ElementCollection
    @CollectionTable(name = "produtos_caracteristicas",
            joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "nome_caracteristica")
    @Column(name = "valor_caracteristica")
    private Map<String, String> caracteristicas;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @PositiveOrZero Integer quantidade,
                   @NotBlank @Length(max = 1000) String descricao,
                   @NotNull Categoria categoria,
                   @NotNull Usuario usuario,
                   @NotNull @Size(min = 3) Map<String, String> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.caracteristicas = caracteristicas;
    }
}
