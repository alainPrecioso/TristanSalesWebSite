package com.aprec.tristan.user.auth.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

	@EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
		System.out.println("test success log");
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
    	System.out.println("test failure log");
    	System.out.println(failures.getException().getMessage());
    }
	
	

}
