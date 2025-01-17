package com.aprec.webapp.user.registration;

import com.aprec.webapp.user.registration.validation.EqualFields;
import com.aprec.webapp.user.registration.validation.Password;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class PasswordRequest extends Password{

	private String token;
	
	public PasswordRequest() {
		super();
	}
	
	public PasswordRequest(String password,
			String passwordcheck,
			String token) {
		super(password, passwordcheck);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
