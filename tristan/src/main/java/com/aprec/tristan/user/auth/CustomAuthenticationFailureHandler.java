package com.aprec.tristan.user.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.aprec.tristan.user.registration.RegistrationService;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
    private MessageSource messages;
	
    @Autowired
    private LocaleResolver localeResolver;
    
    @Autowired
    private RegistrationService registrationService;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        Locale locale = localeResolver.resolveLocale(request);
        log.info("onAuthenticationFailure");
        log.info("exception : [message : " + exception.getMessage() + ", class: " + exception.getClass()+ "]");
        
        String errorMessage = messages.getMessage(exception.getMessage(), null, locale);
        if (exception.getMessage().equalsIgnoreCase("disabled")) {
        	if (registrationService.checkPassword(request.getParameter("username"), request.getParameter("password"))) {
        		registrationService.resendConfirmationMail(request.getParameter("username"));
        	} else {
        		errorMessage = messages.getMessage("badcredentials", null, locale);
        	}
        }

        log.info("localized message : [message : " + errorMessage + "]");
        //request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        request.getSession().setAttribute("alert", errorMessage);
        response.sendRedirect("/login?alert=true");
	}


}
