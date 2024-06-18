package com.aprec.webapp.user.registration.validation;

import javax.validation.constraints.Pattern;

@EqualFields(baseField = "pass", matchField = "passcheck")
public abstract class Password {
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\u00C0-\\u00FF\\u0021-\\u007E]{12,30}$",
			message="passwordpattern")
	protected String password;
	protected String passwordcheck;
	
	protected Password() {
		super();
	}
	protected Password(String password, String passwordcheck) {
		this.password= password;
		this.passwordcheck= passwordcheck;
	}
	
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
