package com.aprec.tristan.user.registration.validation;

import javax.validation.constraints.Pattern;

@EqualFields(baseField = "pass", matchField = "passcheck")
public class Password {
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\u00C0-\\u00FF_\\d]{12,30}$",
			message="passwordpattern")
	protected String pass;
	protected String passcheck;
	
	public Password() {
		super();
	}
	
	public Password(
			String password,
			String passwordcheck) {
		super();
		this.pass = password;
		this.passcheck = passwordcheck;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String password) {
		this.pass = password;
	}

	public String getPasscheck() {
		return passcheck;
	}

	public void setPasscheck(String passwordcheck) {
		this.passcheck = passwordcheck;
	}

}
