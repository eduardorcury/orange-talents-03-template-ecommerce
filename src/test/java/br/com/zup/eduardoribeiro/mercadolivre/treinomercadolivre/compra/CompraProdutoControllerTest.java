package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email.Emails;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CompraProdutoControllerTest {

    static Categoria categoria;
    static Usuario usuario;
    static Produto produto;
    static Collection<NovaCaracteristicaRequest> caracteristicasValidas;
    private NovaCompraRequest compraRequest = new NovaCompraRequest(10, "paypal");

    private EntityManager entityManager = mock(EntityManager.class);
    private Emails emails = mock(Emails.class);
    private CompraProdutoController controller = new CompraProdutoController();

    @BeforeAll
    static void setUp() {
        categoria = new Categoria("categoria");
        usuario = new Usuario("usuario@gmail.com", "senhausuario");
        caracteristicasValidas = List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"));
    }

    @BeforeEach
    void beforeEach() {
        produto = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicasValidas);
    }

    @Test
    void testaCompraProdutoInexistente() {
        ReflectionTestUtils.setField(controller, "entityManager", entityManager);
        when(entityManager.find(Produto.class, 1L)).thenReturn(null);
        ResponseEntity<?> respostaEsperada = ResponseEntity.notFound().build();
        assertEquals(respostaEsperada, controller.compraProduto(1L, compraRequest, usuario));
    }

    @ParameterizedTest
    @MethodSource("geraCompra")
    void testaCompraProduto(int quantidadeComprada, String gateway) {

        NovaCompraRequest request = new NovaCompraRequest(quantidadeComprada, gateway);
        Compra compra = request.converterParaModel(usuario, produto);
        UUID uuid = UUID.randomUUID();
        ReflectionTestUtils.setField(controller, "entityManager", entityManager);
        ReflectionTestUtils.setField(controller, "emails", emails);
        ReflectionTestUtils.setField(compra, "id", 1L);

        when(entityManager.find(Produto.class, 1L)).thenReturn(produto);

        doAnswer(invocation -> {
            Compra compraSalva = invocation.getArgument(0);
            ReflectionTestUtils.setField(compraSalva, "id", 1L);
            ReflectionTestUtils.setField(compraSalva, "codigoDaCompra", uuid);
            return null;
        }).when(entityManager).persist(compra);

        doNothing().when(emails).enviaEmailNovaCompra(compra);

        ResponseEntity<?> resposta = controller.compraProduto(1L, request, usuario);

        ResponseEntity<?> respostaEsperada = quantidadeComprada <= 5
                ? ResponseEntity.status(302).body(compra.geraUrlDaCompra())
                : ResponseEntity.badRequest().body("Parece que não há quantidade suficiente desse produto");

        assertEquals(respostaEsperada, resposta);

    }

    private static Stream<Arguments> geraCompra() {
        return Stream.of(
                Arguments.of(5, "paypal"),
                Arguments.of(10, "paypal"),
                Arguments.of(5, "pagseguro"),
                Arguments.of(10, "pagseguro")
        );
    }


}