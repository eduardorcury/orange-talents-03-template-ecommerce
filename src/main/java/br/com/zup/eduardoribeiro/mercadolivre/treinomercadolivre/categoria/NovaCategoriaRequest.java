package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation.CampoUnico;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation.ExisteEntidade;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NovaCategoriaRequest {

    @NotBlank
    @CampoUnico(campo = "nome", classe = Categoria.class)
    private String nome;

    @ExisteEntidade(entidade = Categoria.class)
    private Long categoriaMaeId;

    public NovaCategoriaRequest(@NotBlank String nome, Long categoriaMaeId) {
        this.nome = nome;
        this.categoriaMaeId = categoriaMaeId;
    }

    public Categoria converterParaModel(EntityManager entityManager) {

        Categoria categoria = new Categoria(this.nome);

        if (categoriaMaeId != null) {
            Categoria categoriaMae = entityManager.find(Categoria.class, categoriaMaeId);
            categoria.setCategoriaMae(categoriaMae);
        }

        return categoria;

    }

}
