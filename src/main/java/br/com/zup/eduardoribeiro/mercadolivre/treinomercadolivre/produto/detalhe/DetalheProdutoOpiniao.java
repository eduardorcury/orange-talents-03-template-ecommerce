package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.detalhe;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao.Opiniao;

public class DetalheProdutoOpiniao {

    private Integer nota;
    private String titulo;
    private String descricao;

    public DetalheProdutoOpiniao(Opiniao opiniao) {
        this.nota = opiniao.getNota();
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
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
