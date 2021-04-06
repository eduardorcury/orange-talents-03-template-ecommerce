package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation.ExisteEntidade;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.validation.constraints.*;

public class NovaOpiniaoRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Length(max = 500)
    private String descricao;

    public NovaOpiniaoRequest(@NotNull @Min(1) @Max(5) Integer nota,
                              @NotBlank String titulo,
                              @NotBlank @Length(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao converterParaModel(Usuario usuario, Produto produto) {

        Assert.notNull(usuario, "Usuário é obrigatório na criação de Opinião");
        Assert.notNull(produto, "Produto é obrigatório na criação de Opinião");

        Assert.isTrue(produto.pertenceAoUsuario(usuario.getUsername()),
                "Produto não pertence ao usuário informado");

        return new Opiniao(this.nota, this.descricao, this.titulo, produto, usuario);

    }

}