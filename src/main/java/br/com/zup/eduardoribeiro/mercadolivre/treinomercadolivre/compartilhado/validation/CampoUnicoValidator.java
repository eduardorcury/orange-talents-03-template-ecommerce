package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root root = criteriaQuery.from(classe);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(campo), parameter));

        Query query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, valor);

        return query.getResultList().isEmpty();

    }
}
