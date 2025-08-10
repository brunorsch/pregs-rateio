package br.app.pregsrateio.common.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MongoIdValidoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoIdValido {
    String message() default "ID inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
