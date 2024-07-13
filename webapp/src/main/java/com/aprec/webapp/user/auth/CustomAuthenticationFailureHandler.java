package com.aprec.webapp.user.auth;

import com.aprec.webapp.user.registration.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Autowired
    private RegistrationService registrationService;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        log.info("onAuthenticationFailure");
        log.info("exception : [message : " + exception.getMessage() + ", class: " + exception.getClass()+ "]");
        
        if (exception.getMessage().equalsIgnoreCase("auth.failure.disabled")) {
        	if (registrationService.checkPassword(request.getParameter("username"), request.getParameter("password"))) {
        		registrationService.resendConfirmationMail(request.getParameter("username"));
				request.getSession().setAttribute("alert", "auth.failure.disabled");

        	} else {
				request.getSession().setAttribute("alert", "auth.failure.badcredentials");
			}

        } else if (exception.getMessage().equalsIgnoreCase("bad credentials")) {
			request.getSession().setAttribute("alert", "auth.failure.badcredentials");
		} else {
			//request.getSession().setAttribute("alert", exception.getMessage());
			request.getSession().setAttribute("alert", "auth.failure.unknown");
		}
		response.sendRedirect("/login?alert");
        //request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        
	}


}
