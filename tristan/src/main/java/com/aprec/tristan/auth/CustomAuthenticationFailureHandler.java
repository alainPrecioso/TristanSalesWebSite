package com.aprec.tristan.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {




	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
//		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//		Locale locale = localeResolver.resolveLocale(request);
//		LocaleContextHolder.getLocale();
//		System.out.println("Locale.getDefault() : " + locale);
//		System.out.println("request.getLocale() : " + request.getLocale());
//		System.out.println("LocaleContextHolder.getLocale()" + LocaleContextHolder.getLocale());
//		System.out.println("localeResolver.resolveLocale(request)" + localeResolver.resolveLocale(request));
//		System.out.println(exception.getLocalizedMessage());
//		System.out.println("exception.getClass() : " + exception.getClass());
		
//		request.setAttribute("error", exception.getLocalizedMessage());
//		request.getServletContext().getRequestDispatcher("/loginerror").forward(request, response);
		response.sendRedirect("/loginerror?error=" + exception.getLocalizedMessage());
	}


}
