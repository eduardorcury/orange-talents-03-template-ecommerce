package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.opiniao.Opiniao;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {

    static Categoria categoria;
    static Usuario usuario;
    static Produto produtoValido;
    static Collection<NovaCaracteristicaRequest> caracteristicasValidas;

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
        produtoValido = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicasValidas);
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

        produtoValido.associaImagens(links);
        assertEquals(tamanho, produtoValido.getImagens().size());

    }

    @ParameterizedTest
    @MethodSource("criaOpinioes")
    void deveCalcularMedia(Double media, List<Opiniao> opinioes) {

        produtoValido.getOpinioes().addAll(opinioes);
        assertEquals(media, produtoValido.calculaMediaNotas());

    }

    @ParameterizedTest
    @MethodSource("criaEstoquesEQuantidades")
    void deveValidarCompraPeloEstoque(Integer estoque, Integer quantidadePedida, boolean resultado) {
        Produto produto = new Produto("nome", BigDecimal.ONE, estoque, "descrição",
                categoria, usuario, caracteristicasValidas);
        boolean abateu = produto.abateEstoque(quantidadePedida);
        assertEquals(resultado, abateu);
    }

    @ParameterizedTest
    @MethodSource("criaQuantidadesInvalidas")
    void naoDeveCalcularQuantidadeInvalida(Integer quantidadePedida) {
        assertThrows(IllegalArgumentException.class, () -> produtoValido.abateEstoque(quantidadePedida));
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

    private static Stream<Arguments> criaOpinioes() {
        return Stream.of(
                Arguments.of(0.0, List.of()),
                Arguments.of(3.0, List.of(new Opiniao(3, "titulo", "descricao", produtoValido, usuario))),
                Arguments.of(2.5, List.of(new Opiniao(5, "titulo", "descricao", produtoValido, usuario),
                        new Opiniao(0, "titulo", "descricao", produtoValido, usuario))));
    }

    private static Stream<Arguments> criaEstoquesEQuantidades() {
        return Stream.of(
                Arguments.of(1, 1, true),
                Arguments.of(5, 2, true),
                Arguments.of(1, 5, false),
                Arguments.of(3, 7, false)
        );
    }

    private static Stream<Integer> criaQuantidadesInvalidas() {
        return Stream.of(0, -1, -100);
    }
}