package com.aprec.tristan.user.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
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

    
    

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        Locale locale = localeResolver.resolveLocale(request);
        System.out.println("onAuthenticationFailure in CustomAuthenticationFailureHandler");
        System.out.println("exception : " + exception.getMessage() + " " + exception.getClass());
        
        String errorMessage = messages.getMessage(exception.getMessage(), null, locale);
        if (exception.getMessage().equalsIgnoreCase("disabled")) {
        	if (registrationService.checkPassword(request.getParameter("username"), request.getParameter("password"))) {
        		registrationService.resendConfirmationMail(request.getParameter("username"));
        		System.out.println("resends mail");
        	} else {
        		errorMessage = messages.getMessage("badcredentials", null, locale);;
        	}
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        response.sendRedirect("/login?error=true");
	}


}
