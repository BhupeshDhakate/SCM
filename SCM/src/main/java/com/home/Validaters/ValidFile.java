package com.home.Validaters;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {

    String message() default "Invalid file";

    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {}; 
}
