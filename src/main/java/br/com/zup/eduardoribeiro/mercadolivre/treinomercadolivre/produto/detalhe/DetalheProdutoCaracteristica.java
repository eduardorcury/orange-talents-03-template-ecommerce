package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.detalhe;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.Caracteristica;

public class DetalheProdutoCaracteristica {

    private String nome;
    private String descricao;

    public DetalheProdutoCaracteristica(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
