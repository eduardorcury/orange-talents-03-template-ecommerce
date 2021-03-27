package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExisteEntidadeValidator implements ConstraintValidator<ExisteEntidade, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entidade;

    @Override
    public void initialize(ExisteEntidade constraintAnnotation) {
        this.entidade = constraintAnnotation.entidade();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        if (id != null) {
            Object objeto = entityManager.find(entidade, id);
            return objeto != null;
        }

        return true;

    }
}
