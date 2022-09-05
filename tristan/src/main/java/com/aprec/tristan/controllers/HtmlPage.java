package com.aprec.tristan.controllers;

public enum HtmlPage {
	INDEX("index"),
	LOGIN("login"),
	REGISTER("register"),
	REGISTER_ERROR("/register?error=true"),
	NEW_PASSWORD("new pass/newpass"),
	FORGOT("new pass/forgot"),
	ERROR("error/error"),
	ERROR_404("error/error-404"),
	ERROR_500("error/error-500");
	
	

	private final String page;
	
	HtmlPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

}
