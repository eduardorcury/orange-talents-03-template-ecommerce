package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.CaracteristicasDuplicadasValidator;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem.NovasImagensRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem.UploaderDev;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UploaderDev uploader;

    @InitBinder(value = "novoProdutoRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new CaracteristicasDuplicadasValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovoProdutoRequest request,
                                       @AuthenticationPrincipal String usuarioLogado) {

        Assert.notNull(usuarioLogado, "Nenhum usuário logado");
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioLogado);

        if (usuarioOptional.isPresent()) {
            Produto produto = request.converterParaModel(entityManager, usuarioOptional.get());
            entityManager.persist(produto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> adicionaImagem(@Valid NovasImagensRequest request,
                                            @PathVariable(name = "id") Long produtoId,
                                            @AuthenticationPrincipal String usuarioLogado) {

        Assert.notNull(usuarioLogado, "Nenhum usuário logado");

        Produto produto = entityManager.find(Produto.class, produtoId);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        } else if (!produto.pertenceAoUsuario(usuarioLogado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Set<URL> links = uploader.envia(request.getImagens());

        produto.associaImagens(links);

        entityManager.merge(produto);
        return ResponseEntity.ok().build();

    }

}
