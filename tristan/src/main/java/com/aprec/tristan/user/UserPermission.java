package com.aprec.tristan.user;

public enum UserPermission {
	PLACEHOLDER_PERMISSION("placeholder:permission");
	
	private final String permission;

	UserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
	

}
