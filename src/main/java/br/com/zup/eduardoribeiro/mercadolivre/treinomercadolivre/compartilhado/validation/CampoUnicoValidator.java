package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> classe;
    private String campo;

    @Override
    public void initialize(CampoUnico constraintAnnotation) {
        this.classe = constraintAnnotation.classe();
        this.campo = constraintAnnotation.campo();
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        return entityManager.createQuery
                ("select 1 from " + classe.getName() + " where " + campo + " = :valor")
                .setParameter("valor", valor)
                .getResultList()
                .isEmpty();

    }
}
