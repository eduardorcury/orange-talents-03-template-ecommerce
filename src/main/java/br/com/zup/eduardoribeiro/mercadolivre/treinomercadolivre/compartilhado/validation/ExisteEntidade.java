package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Checa se existe uma entidade com o ID do campo.
 *
 * ids nulos sao considerados <b>validos</b>
 */
@Documented
@Constraint(validatedBy = ExisteEntidadeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExisteEntidade {

    Class<?> entidade();

    String message() default "Id = ${validatedValue} não encontrado no sistema";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
