package com.aprec.webapp.controllers;

// html attributes
public enum Attribute {
	MESSAGE("message"),
	REQUEST("request"),
	ALERT("alert"),
	PASSWORD_REQUEST("passrequest");
	
	private final String attribute;
	
	Attribute(String string) {
		this.attribute = string;
	}

	public String getAttribute() {
		return attribute;
	}

}
