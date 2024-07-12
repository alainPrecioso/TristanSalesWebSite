package com.aprec.webapp.user.entities;

import java.util.Objects;

import com.aprec.webapp.user.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SiteUser extends User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5275991924990663546L;
	@Column(nullable=false)
	private String password;
	
	protected boolean locked;
	protected boolean enabled;
	
	public SiteUser() {
		super();
	}

	public SiteUser(String username, String email, String password, UserRole userRole) {
		super(username, email, userRole);
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		SiteUser siteUser = (SiteUser) o;
		return locked == siteUser.locked && enabled == siteUser.enabled && Objects.equals(password, siteUser.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), password, locked, enabled);
	}
}

