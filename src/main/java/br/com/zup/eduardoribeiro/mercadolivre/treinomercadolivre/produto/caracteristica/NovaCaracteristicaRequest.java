package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCaracteristicaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public NovaCaracteristicaRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica converterParaModel(@NotNull @Valid Produto produto) {
        return new Caracteristica(this.nome, this.descricao, produto);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
