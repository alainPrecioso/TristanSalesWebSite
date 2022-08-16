package com.aprec.tristan.user;

public enum UserPermission {
	PLACEHOLCER_PERMISSION("placeholder:permission");
	
	private final String permission;

	private UserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
	

}
