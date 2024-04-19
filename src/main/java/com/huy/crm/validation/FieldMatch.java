package com.huy.crm.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = FieldMatchValidator.class)
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface FieldMatch {
    String message() default "Fields values don't match!";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    String first();
    String second();

    @Retention(RUNTIME)
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @interface List {
        FieldMatch[] value();
    }

}
