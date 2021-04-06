package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdicionaOpiniaoController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/produtos/{id}/opinioes")
    @Transactional
    public ResponseEntity<?> adicionaOpiniao(@PathVariable(value = "id") Long produtoId,
                                             @RequestBody @Valid NovaOpiniaoRequest request,
                                             @AuthenticationPrincipal String emailUsuario) {

        Assert.notNull(emailUsuario, "Nenhum usuário logado");
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(emailUsuario);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Produto produto = entityManager.find(Produto.class, produtoId);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        Opiniao opiniao = request.converterParaModel(usuarioOptional.get(), produto);
        entityManager.persist(opiniao);
        return ResponseEntity.ok().build();

    }

}
