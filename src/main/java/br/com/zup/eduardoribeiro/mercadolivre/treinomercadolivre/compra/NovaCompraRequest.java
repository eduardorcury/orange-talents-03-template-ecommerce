package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Locale;

public class NovaCompraRequest {

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private String gateway;

    public NovaCompraRequest(Integer quantidade, String gateway) {
        this.quantidade = quantidade;
        this.gateway = gateway.toUpperCase(Locale.ROOT);
    }

    public Compra converterParaModel(Usuario usuario, Produto produto) {
        return new Compra(this.quantidade, Gateway.valueOf(this.gateway), produto, usuario);
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
