package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.RetornoPagamento;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.RetornoPagamentoPagseguro;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    static Categoria categoria;
    static Usuario usuario;
    static Produto produto;
    static Collection<NovaCaracteristicaRequest> caracteristicasValidas;

    @BeforeAll
    static void setUp() {
        categoria = new Categoria("categoria");
        usuario = new Usuario("usuario@gmail.com", "senhausuario");
        caracteristicasValidas = List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"));
        produto = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicasValidas);
    }

    @ParameterizedTest
    @MethodSource("criaGatewaysDiferentes")
    void deveGerarUrlDaCompra(String gateway) {

        Compra compra = new Compra(5, Gateway.valueOf(gateway.toUpperCase()), produto, usuario);
        UUID uuid = UUID.randomUUID();

        ReflectionTestUtils.setField(compra, "id", 1L);
        ReflectionTestUtils.setField(compra, "codigoDaCompra", uuid);

        String urlRetornada = compra.geraUrlDaCompra();

        if (gateway.equalsIgnoreCase("paypal")) {
            String urlEsperada = "paypal.com?buyerId=" + uuid.toString() + "&redirectUrl=/retorno-paypal/1";
            assertEquals(urlEsperada, urlRetornada);
        } else if (gateway.equalsIgnoreCase("pagseguro")) {
            String urlEsperada = "pagseguro.com?returnId=" + uuid.toString() + "&redirectUrl=/retorno-pagseguro/1";
            assertEquals(urlEsperada, urlRetornada);
        }

    }

    @Test
    void testaAdicionarTransacaoRepetida() {

        Compra compra = new Compra(5, Gateway.PAYPAL, produto, usuario);

        RetornoPagamento retornoPagamento1 = new RetornoPagamentoPagseguro("1", "ERRO");
        compra.adicionaTransacao(retornoPagamento1);

        RetornoPagamento retornoPagamento2 = new RetornoPagamentoPagseguro("1", "SUCESSO");

        assertThrows(IllegalArgumentException.class, () -> compra.adicionaTransacao(retornoPagamento2));

    }

    @Test
    void testaAdicionarTransacaoACompraConcluida() {

        Compra compra = new Compra(5, Gateway.PAYPAL, produto, usuario);

        RetornoPagamento retornoPagamento1 = new RetornoPagamentoPagseguro("1", "SUCESSO");
        compra.adicionaTransacao(retornoPagamento1);

        RetornoPagamento retornoPagamento2 = new RetornoPagamentoPagseguro("2", "SUCESSO");

        assertThrows(IllegalArgumentException.class, () -> compra.adicionaTransacao(retornoPagamento2));

    }

    @Test
    void testaConcluirCompra() {

        Compra compra = new Compra(5, Gateway.PAYPAL, produto, usuario);

        RetornoPagamento retornoPagamento1 = new RetornoPagamentoPagseguro("1", "SUCESSO");
        compra.adicionaTransacao(retornoPagamento1);

        assertTrue(compra.processadaComSucesso());
        
    }

    private static Stream<String> criaGatewaysDiferentes() {
        return Stream.of("paypal", "pagseguro", "PAYPAL", "PAGSEGURO", "Paypal", "Pagseguro");
    }

}