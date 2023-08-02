package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valido = true;

        try {
            BigDecimal valor = (BigDecimal) getPropertyValue(objetoValidacao, valorField);

            String descricao = (String) getPropertyValue(objetoValidacao, descricaoField);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

            return valido;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

    private Object getPropertyValue(Object objetoValidacao, String propertyName) throws InvocationTargetException, IllegalAccessException {
        var propertyDescriptor = BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), propertyName);
        if (propertyDescriptor != null) {
            return propertyDescriptor.getReadMethod().invoke(objetoValidacao);
        }
        return null;
    }

}
