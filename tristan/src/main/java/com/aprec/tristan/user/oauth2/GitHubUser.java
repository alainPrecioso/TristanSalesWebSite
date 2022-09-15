package com.aprec.tristan.user.oauth2;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.User;

@Entity
public class GitHubUser extends User implements OAuth2User {

	private static final long serialVersionUID = 670348870114028786L;
	@Transient
	private OAuth2User oauth2User;
	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	
	private String identifier;
	
    public GitHubUser() {
		super();
	}

	public GitHubUser(OAuth2User oauth2User) {
    	super();
        this.oauth2User = oauth2User;
    }
 
	@Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }
 
    @Override
    public String getName() {
        return oauth2User.getAttribute("login");
    }

	public String getEmail() {
		return oauth2User.getAttribute("email");
	}
	
	public String getUsername() {
		return oauth2User.getAttribute("login");
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String string) {
		this.identifier = string;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setOauth2User(OAuth2User oauth2User) {
		this.oauth2User = oauth2User;
	}
	
}
