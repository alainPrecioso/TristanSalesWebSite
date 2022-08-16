package com.aprec.tristan.user.registration;

import java.util.Objects;

public class RegistrationRequest {
	
	private final String username;
	private final String email;
	private final String password;
	
	public RegistrationRequest(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
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
		return "RegistrationRequest [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	
	
}
