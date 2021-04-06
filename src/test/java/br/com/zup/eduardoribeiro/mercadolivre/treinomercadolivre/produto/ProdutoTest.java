package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoTest {

    static Categoria categoria;
    static Usuario usuario;

    @BeforeAll
    static void setUp() {
        categoria = new Categoria("categoria");
        usuario = new Usuario("usuario@gmail.com", "senhausuario");
    }

    @ParameterizedTest
    @MethodSource("tresOuMaisCaracteristicas")
    void deveCriarProdutosComTresOuMaisCaracteristicas(Collection<NovaCaracteristicaRequest> caracteristicas) {

        Produto produto = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicas);

    }

    @ParameterizedTest
    @MethodSource("menosDeTresCaracteristicas")
    void naoDeveCriarProdutoComMenosDeTresCaracteristicas(Collection<NovaCaracteristicaRequest> caracteristicas) {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Produto produto = new Produto("nome", BigDecimal.ONE, 5,
                    "descrição", categoria, usuario, caracteristicas);
        });
    }

    @ParameterizedTest
    @MethodSource("criaURLs")
    void deveAdicionarImagensAoProduto(int tamanho, Set<URL> links) {

        Collection<NovaCaracteristicaRequest> caracteristicas =
                List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                        new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                        new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"));

        Produto produto = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicas);

        produto.associaImagens(links);

        assertEquals(tamanho, produto.getImagens().size());

    }

    private static Stream<Arguments> tresOuMaisCaracteristicas() {
        return Stream.of(
                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                                new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                                new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"))),

                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                                new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                                new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"),
                                new NovaCaracteristicaRequest("Caracteristica 4", "Descrição 4")))
        );
    }

    private static Stream<Arguments> menosDeTresCaracteristicas() {
        return Stream.of(
                Arguments.of(List.of()),
                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"))),
                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                        new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2")))

        );
    }

    private static Stream<Arguments> criaURLs() throws MalformedURLException {
        return Stream.of(
                Arguments.of(0, Set.of()),
                Arguments.of(1, Set.of(new URL("http://teste.com"))),
                Arguments.of(3, Set.of(new URL("http://teste1.com"),
                        new URL("http://teste2.com"),
                        new URL("http://teste3.com"))));
    }
}