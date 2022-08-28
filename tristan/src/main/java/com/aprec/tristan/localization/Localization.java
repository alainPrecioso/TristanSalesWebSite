package com.aprec.tristan.localization;

import java.util.Locale;
import java.util.function.BiFunction;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class Localization implements WebMvcConfigurer{
	
		
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = 
	    		new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	SessionLocaleResolver localeResolver() {
	   SessionLocaleResolver localeResolver = 
			   new SessionLocaleResolver();
	   localeResolver.setDefaultLocale(Locale.FRENCH);
	   return localeResolver;
	}
	
//	@Bean
//	CookieLocaleResolver localeResolver() {
//	    CookieLocaleResolver localeResolver 
//	      = new CookieLocaleResolver();
//	    localeResolver.setDefaultLocale(Locale.FRENCH);
//	    localeResolver.setCookieName("locale");
//	    localeResolver.setCookieMaxAge(3600);
//	    return localeResolver;
//	}
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
	     LocaleChangeInterceptor localeChangeInterceptor = 
	    		 new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("lang");
	     return localeChangeInterceptor;
	}
	
	@Bean
	public BiFunction<String, String, String> replaceOrAddParam() {
	  return (paramName, newValue) -> ServletUriComponentsBuilder
			.fromCurrentRequest()
	        .replaceQueryParam(paramName, newValue)
	        .toUriString();
	}
	
	
	
}
