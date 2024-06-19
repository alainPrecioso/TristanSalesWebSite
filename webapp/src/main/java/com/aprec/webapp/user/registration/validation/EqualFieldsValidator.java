package com.aprec.webapp.user.registration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualFieldsValidator 
	implements ConstraintValidator<EqualFields, Password> {
	 
    @Override
    public boolean isValid(Password form, ConstraintValidatorContext context) {
        return form.getPassword().equals(form.getPasswordcheck());
    }
 
}
