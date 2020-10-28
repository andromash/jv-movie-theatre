package com.hibernate.cinema.validator;

import com.hibernate.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsValueMatchValidator
        implements ConstraintValidator<PasswordsValueMatch, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto value, ConstraintValidatorContext context) {
        String fieldValue = value.getPassword();
        String fieldMatchValue = value.getPasswordRepeated();

        return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }
}
