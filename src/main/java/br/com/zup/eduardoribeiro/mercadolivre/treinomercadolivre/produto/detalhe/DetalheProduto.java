package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.detalhe;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem.ImagemProduto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta.Pergunta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class DetalheProduto {

    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Set<String> links;
    private Set<DetalheProdutoCaracteristica> caracteristicas;
    private List<DetalheProdutoOpiniao> opinioes;
    private List<String> perguntas;
    private Integer totalDeNotas;
    private Double mediaDeNotas;

    public DetalheProduto(@NotNull @Valid Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.descricao = produto.getDescricao();
        this.links = produto.converteImagens(ImagemProduto::getLink);
        this.caracteristicas = produto.converteCaracteristica(DetalheProdutoCaracteristica::new);
        this.opinioes = produto.converteOpiniao(DetalheProdutoOpiniao::new);
        this.perguntas = produto.convertePergunta(Pergunta::getTitulo);
        this.totalDeNotas = produto.retornaTotalDeNotas();
        this.mediaDeNotas = produto.calculaMediaNotas();
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

    public Set<String> getLinks() {
        return links;
    }

    public Set<DetalheProdutoCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public List<DetalheProdutoOpiniao> getOpinioes() {
        return opinioes;
    }

    public List<String> getPerguntas() {
        return perguntas;
    }

    public Integer getTotalDeNotas() {
        return totalDeNotas;
    }

    public Double getMediaDeNotas() {
        return mediaDeNotas;
    }
}
