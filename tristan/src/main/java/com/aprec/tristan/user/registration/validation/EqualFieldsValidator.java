package com.aprec.tristan.user.registration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aprec.tristan.user.registration.PasswordRequest;

public class EqualFieldsValidator 
	implements ConstraintValidator<EqualFields, PasswordRequest> {
	 
    @Override
    public void initialize(EqualFields constraint) {
    }
 
    @Override
    public boolean isValid(PasswordRequest form, ConstraintValidatorContext context) {
        return form.getPassword().equals(form.getPasswordcheck());
    }
 
}
