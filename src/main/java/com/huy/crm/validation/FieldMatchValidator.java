package com.huy.crm.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    String firstFieldName;
    String secondFieldName;
    String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            Object first = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            Object second = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            valid = first == null && second == null || first != null && first.equals(second);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

}