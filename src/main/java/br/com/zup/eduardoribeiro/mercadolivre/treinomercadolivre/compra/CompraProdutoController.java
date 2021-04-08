package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email.Emails;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class CompraProdutoController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Emails emails;

    @PostMapping(value = "/produtos/{id:^[0-9]*$}/compra")
    @Transactional
    public ResponseEntity<?> compraProduto(@PathVariable(value = "id") Long produtoId,
                                           @RequestBody @Valid NovaCompraRequest request,
                                           @AuthenticationPrincipal Usuario usuarioLogado) {

        Assert.notNull(usuarioLogado, "Nenhum usuário logado");

        Produto produto = entityManager.find(Produto.class, produtoId);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        if (!produto.abateEstoque(request.getQuantidade())) {
            return ResponseEntity.badRequest()
                    .body("Parece que não há quantidade suficiente desse produto");
        }

        Compra compra = request.converterParaModel(usuarioLogado, produto);
        entityManager.persist(compra);

        emails.enviaEmailNovaCompra(compra);
        return ResponseEntity.status(302).body(compra.geraUrlDaCompra());

    }
}
