package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation.ExisteEntidade;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Map;

public class NovoProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExisteEntidade(entidade = Categoria.class)
    private Long categoriaId;

    @NotNull
    @Size(min = 3)
    private Map<String, String> caracteristicas;

    public NovoProdutoRequest(@NotBlank String nome,
                              @NotNull @Positive BigDecimal valor,
                              @NotNull @PositiveOrZero Integer quantidade,
                              @NotBlank @Length(max = 1000) String descricao,
                              @NotNull Long categoriaId,
                              @NotNull @Size(min = 3) Map<String, String> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.caracteristicas = caracteristicas;
    }

    public Produto converterParaModel(EntityManager entityManager, Usuario usuarioLogado) {

        Assert.notNull(this.categoriaId, "É necessário o id da Categoria");
        Categoria categoria = entityManager.find(Categoria.class, categoriaId);
        Assert.notNull(categoria, "Categoria de id " + this.categoriaId + " não encontrada");

        Assert.notNull(usuarioLogado, "Nenhum usuário logado");
        Assert.isTrue(this.caracteristicas.size() >= 3, "Produto deve ter no mínimo 3 características");

        return new Produto(this.nome, this.valor, this.quantidade,
                this.descricao, categoria, usuarioLogado, this.caracteristicas);

    }
}
