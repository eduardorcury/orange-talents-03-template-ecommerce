package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.detalhe;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class DetalheProdutoController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/produtos/{id:^[0-9]*$}")
    public ResponseEntity<?> detalhaProduto(@PathVariable(name = "id") Long produtoId) {

        Produto produto = entityManager.find(Produto.class, produtoId);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        DetalheProduto detalhe = new DetalheProduto(produto);
        return ResponseEntity.ok(detalhe);

    }

}
