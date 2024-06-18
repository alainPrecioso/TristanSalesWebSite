package com.aprec.tristan.controllers;

public enum HtmlPage {
	INDEX("index"),
	INDEX_REDIRECT("redirect:/"),
	INDEX_REDIRECT_MESSAGE("redirect:/?message=true"),
	LOGIN("login"),
	LOGIN_MESSAGE("/login?message=true"),
	LOGIN_REDIRECT("redirect:/login?message=true"),
	LOGIN_ALERT("redirect:login?alert=true"),
	REGISTER("register"),
	REGISTER_ALERT("redirect:register?alert=true"),
	NEW_PASSWORD("new pass/newpass"),
	FORGOT("new pass/forgot"),
	ERROR("error/error"),
	ERROR_404("error/error-404"),
	ERROR_500("error/error-500");

	private final String path;

	HtmlPage(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
