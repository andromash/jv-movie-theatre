package com.hibernate.cinema.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordsValueMatchValidator
        implements ConstraintValidator<PasswordsValueMatch, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);

            return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }
}
