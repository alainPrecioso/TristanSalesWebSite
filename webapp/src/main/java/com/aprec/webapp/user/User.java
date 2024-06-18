package com.aprec.webapp.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

	@Id
	@SequenceGenerator(
			name="user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence")
	protected Long id;
	protected String username;
	protected String email;
	@Enumerated(EnumType.STRING)
	protected UserRole userRole;
	protected LocalDateTime deleteTime;
	protected boolean deleteScheduled;
	protected String userType;
	
	public User() {
		super();
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
		return Collections.singletonList(authority);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public LocalDateTime getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(LocalDateTime deleteTime) {
		this.deleteTime = deleteTime;
	}
	public boolean isDeleteScheduled() {
		return deleteScheduled;
	}
	public void setDeleteScheduled(boolean deleteScheduled) {
		this.deleteScheduled = deleteScheduled;
	}
	
	public long getDaysToDeletion() {
		if (this.deleteScheduled) {
			return LocalDateTime.now().until(this.deleteTime,ChronoUnit.DAYS);
		}
		return 0l;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deleteScheduled, deleteTime, email, id, userRole, userType, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return deleteScheduled == other.deleteScheduled && Objects.equals(deleteTime, other.deleteTime)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id) && userRole == other.userRole
				&& Objects.equals(userType, other.userType) && Objects.equals(username, other.username);
	}
	
	
	
}
