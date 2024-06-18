package com.aprec.webapp.user.registration.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EqualFieldsValidator.class})
public @interface EqualFields {
 
    String message() default "{fields not equal}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
 
    String baseField();
 
    String matchField();
 
}
