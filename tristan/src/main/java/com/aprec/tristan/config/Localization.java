package com.aprec.tristan.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class Localization implements WebMvcConfigurer{
	
		

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	LocaleResolver localeResolver() {
	   SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	   localeResolver.setDefaultLocale(Locale.FRENCH);
	   return localeResolver;
	}
	
	@Bean
	CookieLocaleResolver cookieLocaleResolverExample() {
	    CookieLocaleResolver localeResolver 
	      = new CookieLocaleResolver();
	    localeResolver.setDefaultLocale(Locale.FRENCH);
	    localeResolver.setCookieName("locale");
	    localeResolver.setCookieMaxAge(3600);
	    return localeResolver;
	}
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
	     LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("lang");
	     return localeChangeInterceptor;
	}
	
	
	
	
	
}