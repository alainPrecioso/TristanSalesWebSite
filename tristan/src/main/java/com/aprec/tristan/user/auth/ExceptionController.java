package com.aprec.tristan.user.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aprec.tristan.user.registration.RegistrationService;

@ControllerAdvice
public class ExceptionController {
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private RegistrationService registrationService;
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(IllegalStateException.class)
	public void handleExceptionIllegalStateException(IllegalStateException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		log.info("handleExceptionIllegalStateException");
		
		switch(e.getMessage()) {
			case "tokenexpired":
				registrationService.resendConfirmationMailFromToken(request.getParameter("token"));
				// fallthrough
			case "emailalreadyconfirmed", "userexists":
				String errorMessage = messages.getMessage(e.getMessage(), null, locale);
				request.getSession().setAttribute("errormessage", errorMessage);
				response.sendRedirect("/register?error=true");
				break;
		  case "tokennotfound":
		    // code block
		    break;
		  default:
			  break;
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public void handleBadCredentialsException(BadCredentialsException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		log.info("handleBadCredentialsException");
		String errorMessage = messages.getMessage(e.getMessage(), null, locale);
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
		response.sendRedirect("/login?error=true");
	}
}
