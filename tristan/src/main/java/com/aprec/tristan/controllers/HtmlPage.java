package com.aprec.tristan.controllers;

public enum HtmlPage {
	INDEX("index"),
	INDEX_REDIRECT("redirect:/index"),
	LOGIN("login"),
	LOGIN_ALERT("redirect:login?alert=true"),
	REGISTER("register"),
	REGISTER_ALERT("redirect:register?alert=true"),
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
