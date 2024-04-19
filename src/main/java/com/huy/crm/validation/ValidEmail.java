package com.huy.crm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidEmailValidator.class)
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ValidEmail {
    String message() default "Email is invalid";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
