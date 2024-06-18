package com.aprec.tristan.user.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.aprec.tristan.user.SiteUser;

@Entity
public class PasswordToken {

	@Id
	@SequenceGenerator(
			name="passtoken_sequence",
			sequenceName = "passtoken_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "passtoken_sequence")
	private long id;
	@Column(nullable=false)
	private String token;
	@Column(nullable=false)
	private LocalDateTime creationTime;
	@Column(nullable=false)
	private LocalDateTime expirationTime;
	private LocalDateTime confirmationTime;
	@OneToOne
	@JoinColumn(nullable=false, name= "user_id")
	private SiteUser user;

	
	
	public PasswordToken() {
		super();
	}

	public PasswordToken(String token, LocalDateTime localDateTime, LocalDateTime localDateTime2,
			SiteUser user) {
		super();
		this.token = token;
		this.creationTime = localDateTime;
		this.expirationTime = localDateTime2;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public LocalDateTime getConfirmationTime() {
		return confirmationTime;
	}

	public void setConfirmationTime(LocalDateTime confirmationTime) {
		this.confirmationTime = confirmationTime;
	}
	
	
	
	
	
}
