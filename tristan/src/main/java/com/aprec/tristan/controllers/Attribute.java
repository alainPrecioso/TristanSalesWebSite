package com.aprec.tristan.controllers;

public enum Attribute {
	MESSAGE("message"),
	REQUEST("request");
	
	private final String attribute;
	
	Attribute(String string) {
		this.attribute = string;
	}

	public String getAttribute() {
		return attribute;
	}

}
