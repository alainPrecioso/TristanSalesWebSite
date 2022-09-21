package com.aprec.tristan.user;

public interface UserServiceInterface {

	public String signUpUser(SiteUser user);
	
	public String getNewToken(SiteUser user);
	
	public int enableUser(String email);
	
	public SiteUser getSiteUser(String credential);
	
	public void updatePassword(SiteUser user, String password);
	
	public boolean checkPassword(String username, String rawPassword);
	
	public void deleteUser(User user);
	
	public void scheduleDelete();
	
	public void deleteUsers();
	
	public User getUserWithType(String username, String type);
	
	public User getLoggedUser();

	public void cancelDelete();
}
