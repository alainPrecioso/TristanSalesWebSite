package com.aprec.tristan.user.registration.validation;

import javax.validation.constraints.Pattern;

@EqualFields(baseField = "pass", matchField = "passcheck")
public class Password {
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\u00C0-\\u00FF_\\d]{12,30}$",
			message="passwordpattern")
	protected String password;
	protected String passwordcheck;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordcheck() {
		return passwordcheck;
	}
	public void setPasswordcheck(String passwordcheck) {
		this.passwordcheck = passwordcheck;
	}
	

}
