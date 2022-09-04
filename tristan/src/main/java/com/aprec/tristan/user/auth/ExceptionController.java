package com.aprec.tristan.user.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@Autowired
    private MessageSource messages;
	
	@ExceptionHandler(IllegalStateException.class)
	public void handleException(IllegalStateException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		System.out.println("handleException in ExceptionController");
		
		switch(e.getMessage()) {
		  case "tokenexpired", "emailalreadyconfirmed":
			  String errorMessage = messages.getMessage(e.getMessage(), null, locale);
			  request.getSession().setAttribute("errormessage", errorMessage);
			  response.sendRedirect("/register?error=true");
		    break;
		  case "tokennotfound":
		    // code block
		    break;
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public void handleBadCredentialsException(BadCredentialsException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		System.out.println("handleBadCredentialsException in ExceptionController");
		String errorMessage = messages.getMessage(e.getMessage(), null, locale);
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
		response.sendRedirect("/login?error=true");
	}
}
