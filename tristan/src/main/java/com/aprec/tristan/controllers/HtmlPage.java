package com.aprec.tristan.controllers;

public enum HtmlPage {
	INDEX("index"),
	LOGIN("login"),
	REGISTER("register"),
	NEW_PASSWORD("new pass/newpass"),
	FORGOT("new pass/forgot");
	
	

	private final String page;
	
	HtmlPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

}
