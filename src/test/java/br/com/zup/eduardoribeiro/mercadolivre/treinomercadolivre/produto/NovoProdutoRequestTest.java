package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class NovoProdutoRequestTest {

    @ParameterizedTest
    @MethodSource("criaCaracteristicas")
    void deveRetornarCaracteristicasDuplicadas(Integer tamanho, List<NovaCaracteristicaRequest> caracteristicas) {
        NovoProdutoRequest request = new NovoProdutoRequest("nome", BigDecimal.ONE, 10,
                "descricao", 1L, caracteristicas);
        Set<String> duplicadas = request.buscaCaracteristicasDuplicadas();
        Assertions.assertEquals(tamanho, duplicadas.size());

    }

    private static Stream<Arguments> criaCaracteristicas() {
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"))),
                Arguments.of(0, List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1")),
                        new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2")),
                Arguments.of(0, List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1")),
                        new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"))
        );
    }

}