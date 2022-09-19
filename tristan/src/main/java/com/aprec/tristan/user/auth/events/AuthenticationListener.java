package com.aprec.tristan.user.auth.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationListener.class);

	@EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
		log.debug("AuthenticationListener success log");
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
    	log.info("AuthenticationListener failure log");
    	log.info(failures.getException().getMessage());
    }
	
	

}
