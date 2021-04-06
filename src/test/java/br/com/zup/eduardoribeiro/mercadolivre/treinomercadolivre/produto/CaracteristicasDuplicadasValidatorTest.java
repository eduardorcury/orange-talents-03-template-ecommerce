package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.CaracteristicasDuplicadasValidator;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class CaracteristicasDuplicadasValidatorTest {

    @ParameterizedTest
    @MethodSource("criaCaracteristicas")
    void validaCaracteristicasDuplicadasDeRequestSemOutrosErros(boolean esperado, List<NovaCaracteristicaRequest> caracteristicas) {

        CaracteristicasDuplicadasValidator validator = new CaracteristicasDuplicadasValidator();

        NovoProdutoRequest request = new NovoProdutoRequest("nome", BigDecimal.ONE, 10,
                "descricao", 1L, caracteristicas);

        Errors erros = new BeanPropertyBindingResult(request, "caracteristicas");
        validator.validate(request, erros);

        Assertions.assertEquals(esperado, erros.hasFieldErrors("caracteristicas"));

    }

    @ParameterizedTest
    @MethodSource("criaSomenteCaracteristicas")
    void validaCaracteristicasDuplicadasDeRequestComOutrosErros(List<NovaCaracteristicaRequest> caracteristicas) {

        CaracteristicasDuplicadasValidator validator = new CaracteristicasDuplicadasValidator();

        NovoProdutoRequest request = new NovoProdutoRequest("nome", BigDecimal.ONE, 10,
                "descricao", 1L, caracteristicas);

        Errors erros = new BeanPropertyBindingResult(request, "caracteristicas");
        erros.rejectValue("nome", null);
        validator.validate(request, erros);

        Assertions.assertFalse(erros.hasFieldErrors("caracteristicas"));

    }

    private static Stream<Arguments> criaCaracteristicas() {
        return Stream.of(
                Arguments.of(false, List.of()),
                Arguments.of(false, List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1"))),
                Arguments.of(true, List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1"),
                                            new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1")))
        );
    }

    private static Stream<Arguments> criaSomenteCaracteristicas() {
        return Stream.of(
                Arguments.of(List.of()),
                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1"))),
                Arguments.of(List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1"),
                        new NovaCaracteristicaRequest("Caracteristica 1", "Descricao 1")))
        );
    }

}