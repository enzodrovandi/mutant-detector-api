package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
public @interface ValidDnaSequence {

    String message() default "La secuencia de ADN es inválida. Debe ser una matriz NxN (mínimo 4x4) con caracteres A, T, C, G.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
