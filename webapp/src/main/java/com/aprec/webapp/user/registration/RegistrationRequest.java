package com.aprec.webapp.user.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import com.aprec.webapp.user.registration.validation.EqualFields;
import com.aprec.webapp.user.registration.validation.Password;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class RegistrationRequest extends Password {
	
	@NotEmpty
	@NotBlank(message = "usernamerequired")
	@Pattern(regexp="^(?!.*^_|.*_$|.*__|.*^-|.*-$|.*--)[a-zA-Z\\u00C0-\\u00FF_\\d-]{5,25}$",
			message="usernamepattern")
	private String username;
	
	@NotBlank(message = "emailrequired")
	@Email(message = "emailinvalid")
	private String email;
	
	
	
	public RegistrationRequest() {
		super();
	}


	public RegistrationRequest(String password, String passwordcheck, String username, String email) {
		super(password, passwordcheck);
		this.username = username;
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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
