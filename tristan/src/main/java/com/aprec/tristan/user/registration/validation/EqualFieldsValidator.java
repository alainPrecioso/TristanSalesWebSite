package com.aprec.tristan.user.registration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aprec.tristan.user.registration.RegistrationRequest;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, RegistrationRequest> {
	 
    @Override
    public void initialize(EqualFields constraint) {
    }
 
    @Override
    public boolean isValid(RegistrationRequest form, ConstraintValidatorContext context) {
        return form.getPassword().equals(form.getPasswordcheck());
    }
 
}
