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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
    private MessageSource messages;
	
    @Autowired
    private LocaleResolver localeResolver;


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/loginerror?error=true");
		super.onAuthenticationFailure(request, response, exception);

        Locale locale = localeResolver.resolveLocale(request);
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
//		if (exception.getLocalizedMessage().equals("disabled")) {
//			response.sendRedirect("/test");
//		} else {
//			response.sendRedirect("/loginerror?error=" + exception.getLocalizedMessage());
//		}
		
        String errorMessage = messages.getMessage("message.badCredentials", null, locale);

        if (exception.getMessage().equalsIgnoreCase("disabled")) {
            errorMessage = messages.getMessage("disabled", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}


}
