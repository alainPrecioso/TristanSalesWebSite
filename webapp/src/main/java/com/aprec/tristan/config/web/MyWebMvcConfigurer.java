package com.aprec.tristan.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.aprec.tristan.config.web.localization.Localization.localeChangeInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    //region Localization
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // allows HtmlPage to be available as a return type fo controllers
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MyViewEnumModelAndViewResolver());
    }



}
