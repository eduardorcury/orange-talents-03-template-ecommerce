package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.NovoProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class CaracteristicasDuplicadasValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoProdutoRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            return;
        }

        NovoProdutoRequest request = (NovoProdutoRequest) target;

        Set<String> nomesDuplicados = request.buscaCaracteristicasDuplicadas();

        if (!nomesDuplicados.isEmpty()) {
            errors.rejectValue("caracteristicas", null,
                    "Existem categorias duplicadas: " + nomesDuplicados);
        }

    }
}
