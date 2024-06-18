package com.aprec.webapp.user.token;

import com.aprec.webapp.user.SiteUser;
import com.aprec.webapp.user.User;

import java.util.Optional;

public interface PasswordTokenServiceInterface {

    void deletePasswordToken(String token);

    Optional<PasswordToken> getToken(String token);

    String createNewToken(SiteUser user);

    String createPasswordToken(SiteUser user);

    void delete(User user);
}
