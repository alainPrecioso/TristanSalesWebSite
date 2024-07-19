package com.aprec.webapp.user;

import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String signUpUser(SiteUser user);

    String getNewToken(SiteUser user);

    int enableUser(String email);

    SiteUser getSiteUser(String credential);

    void updatePassword(SiteUser user, String password);

    boolean checkPassword(String username, String rawPassword);

    void deleteUser(User user);

    void scheduleDelete();

    void deleteUsers();

    User getUserWithType(String username, String type);

    User getLoggedUser();

    void cancelDelete();
}
