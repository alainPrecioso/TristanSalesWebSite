package com.aprec.tristan.users;

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
