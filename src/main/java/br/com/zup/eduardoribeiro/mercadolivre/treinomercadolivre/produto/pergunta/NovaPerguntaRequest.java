package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaPerguntaRequest {

    @NotBlank
    private String titulo;

    @JsonCreator
    public NovaPerguntaRequest(@NotBlank @JsonProperty("titulo") String titulo) {
        this.titulo = titulo;
    }

    public Pergunta converterParaModel(@NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {

        Assert.notNull(usuario, "Usuário é obrigatório na criação de Opinião");
        Assert.notNull(produto, "Produto é obrigatório na criação de Opinião");

        return new Pergunta(this.titulo, usuario, produto);
    }

}
