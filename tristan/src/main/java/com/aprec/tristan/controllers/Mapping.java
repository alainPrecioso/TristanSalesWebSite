package com.aprec.tristan.controllers;

import java.util.Arrays;

public enum Mapping {
	
	INDEX(new String[] {"/", "/index", "/home" });
	
	private final String[] values;

	Mapping(String[] strings) {
		this.values = strings;
	}

	public String[] getValues() {
		return values;
	}
	
	

}
