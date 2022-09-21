package com.aprec.tristan.user.registration;

import com.aprec.tristan.user.registration.validation.EqualFields;
import com.aprec.tristan.user.registration.validation.Password;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class PasswordRequest extends Password{

	private String token;
	
	public PasswordRequest() {
		super();
	}
	
	public PasswordRequest(String password,
			String passwordcheck,
			String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
