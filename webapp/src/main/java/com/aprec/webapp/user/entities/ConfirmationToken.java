package com.aprec.webapp.user.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
//	@SequenceGenerator(
//			name="token_sequence",
//			sequenceName = "token_sequence",
//			allocationSize = 1)
//	@GeneratedValue(
//			strategy = GenerationType.SEQUENCE,
//			generator = "token_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime creationTime;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    private LocalDateTime confirmationTime;
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


    public ConfirmationToken() {
        super();
    }

    public ConfirmationToken(String token, LocalDateTime localDateTime, LocalDateTime localDateTime2,
                             User user) {
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

    public LocalDateTime getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(LocalDateTime confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
