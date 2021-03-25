package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Verifica se o campo informado é único no banco de dados para a entidade recebida
 * no parâmetro.
 *
 * O campo anotado deve ser {@code String}.
 */
@Documented
@Constraint(validatedBy = CampoUnicoValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CampoUnico {

    /**
     * Deve ser o nome do campo da <b>entidade</b>.
     */
    String campo();

    /**
     * Deve ser a classe da <b>entidade</b>.
     */
    Class<?> classe();

    String message() default "${validatedValue} já cadastrado no sistema";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
