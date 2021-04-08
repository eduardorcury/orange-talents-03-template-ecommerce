package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class AdicionaOpiniaoController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/produtos/{id:^[0-9]*$}/opinioes")
    @Transactional
    public ResponseEntity<?> adicionaOpiniao(@PathVariable(value = "id") Long produtoId,
                                             @RequestBody @Valid NovaOpiniaoRequest request,
                                             @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = entityManager.find(Produto.class, produtoId);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        Opiniao opiniao = request.converterParaModel(usuarioLogado, produto);
        entityManager.persist(opiniao);
        return ResponseEntity.ok().build();

    }

}
