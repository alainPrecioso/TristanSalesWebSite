package com.aprec.tristan.user.registration;

import java.util.Objects;

import javax.validation.constraints.Pattern;

import com.aprec.tristan.user.registration.validation.EqualFields;

@EqualFields(baseField = "password", matchField = "passwordcheck")
public class PasswordRequest {

	private String token;
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{12,30}$", 
			message="passwordpattern")
	private String password;
	private String passwordcheck;
	
	public PasswordRequest() {
		super();
	}
	
	public PasswordRequest(String token,
			@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{12,30}$", message = "passwordpattern") String password,
			String passwordcheck) {
		super();
		this.token = token;
		this.password = password;
		this.passwordcheck = passwordcheck;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	@Override
	public String toString() {
		return "NewPasswordRequest [token=" + token + ", password=" + password + ", passwordcheck=" + passwordcheck
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, passwordcheck, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordRequest other = (PasswordRequest) obj;
		return Objects.equals(password, other.password) && Objects.equals(passwordcheck, other.passwordcheck)
				&& Objects.equals(token, other.token);
	}
	
}
