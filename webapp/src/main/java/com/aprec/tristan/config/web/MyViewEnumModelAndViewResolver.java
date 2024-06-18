package com.aprec.tristan.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.aprec.tristan.controllers.HtmlPage;

// allows HtmlPage to be available as a return type fo controllers
public class MyViewEnumModelAndViewResolver implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return HtmlPage.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		if (returnValue == null) {
		      return;
		    }
		    if (returnValue instanceof HtmlPage htmlPage) {
		      mavContainer.setViewName(htmlPage.getPath());
		    }
		    else {
		      throw new UnsupportedOperationException("Unexpected return type: " +
		        returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
		    }

	}

}
