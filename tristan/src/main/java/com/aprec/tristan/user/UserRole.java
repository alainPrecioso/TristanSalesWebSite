package com.aprec.tristan.user;

import static com.aprec.tristan.user.UserPermission.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
	ROLE_USER(new HashSet<UserPermission>()),
	ROLE_ADMIN(new HashSet<UserPermission>(Arrays.asList(PLACEHOLCER_PERMISSION)));

	private final Set<UserPermission> permissions;

	private UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}
	
}
