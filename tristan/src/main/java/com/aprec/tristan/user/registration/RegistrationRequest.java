package com.aprec.tristan.user.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.aprec.tristan.user.registration.validation.EqualFields;
import com.aprec.tristan.user.registration.validation.Password;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class RegistrationRequest extends Password {
	
	@NotEmpty
	@NotBlank(message = "usernamerequired")
	@Pattern(regexp="^(?!.*^_)(?!.*_$)(?!.*__)[a-zA-Z\\u00C0-\\u00FF_\\d]{5,45}$", 
			message="usernamepattern")
	private String username;
	
	@NotBlank(message = "emailrequired")
	@Email(message = "emailinvalid")
	private String email;
	
	public RegistrationRequest(String password, String passwordcheck, String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}


	public RegistrationRequest() {
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
