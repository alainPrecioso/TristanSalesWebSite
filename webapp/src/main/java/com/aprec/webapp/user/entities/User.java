package com.aprec.webapp.user.entities;

import com.aprec.webapp.user.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
//	@SequenceGenerator(
//			name="user_sequence",
//			sequenceName = "user_sequence",
//			allocationSize = 1)
//	@GeneratedValue(
//			strategy = GenerationType.SEQUENCE,
//			generator = "user_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private LocalDateTime deleteTime;
    private boolean deleteScheduled;
    private String userType;

    public User() {
        super();
    }

    public User(String username, String email, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.userRole = userRole;
    }

    public User(UserRole userRole) {
        this.userRole = userRole;
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

    public void setUsername(String username) {
        this.username = username;
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
            return LocalDateTime.now().until(this.deleteTime, ChronoUnit.DAYS);
        }
        return 0L;
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
