package com.aprec.tristan.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SiteUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5275991924990663546L;
	@Column(unique = true, nullable=false)
	private String username;
	@Column(unique = true, nullable=false)
	private String email;
	@Column(nullable=false)
	private String password;
	
	public SiteUser() {
		super();
	}
	public SiteUser(String username, String email, String password, UserRole userRole) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		super.userRole = userRole;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(email, username);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteUser other = (SiteUser) obj;
		return Objects.equals(email, other.email) && Objects.equals(username, other.username);
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
	

	
}

