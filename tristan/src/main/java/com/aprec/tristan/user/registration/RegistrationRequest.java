package com.aprec.tristan.user.registration;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.aprec.tristan.user.registration.validation.EqualFields;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class RegistrationRequest {
	
	@NotEmpty
	@NotBlank(message = "usernamerequired")
	@Pattern(regexp="^(?!.*^_)(?!.*_$)(?!.*__)[a-zA-Z\\u00C0-\\u00FF_\\d]{5,45}$", 
			message="usernamepattern")
	private String username;
	
	@NotBlank(message = "emailrequired")
	@Email(message = "emailinvalid")
	private String email;
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{12,30}$", 
			message="passwordpattern")
	private String password;
	private String passwordcheck;
	
	
	public RegistrationRequest(String username, String email, String password, String passwordcheck) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.passwordcheck = passwordcheck;
	}


	public RegistrationRequest() {
	}

	public static RegistrationRequest create() {
		return new RegistrationRequest();
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


	@Override
	public int hashCode() {
		return Objects.hash(email, password, username);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	
	@Override
	public String toString() {
		return "RegistrationRequest [username=" + username + 
				", email=" + email + ", password=" + password + "]";
	}


	public String getPasswordcheck() {
		return passwordcheck;
	}

	
	public void setPasswordcheck(String passwordcheck) {
		this.passwordcheck = passwordcheck;
	}
}
