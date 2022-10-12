package com.aprec.tristan.user.token;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.User;

import java.util.Optional;

public interface PasswordTokenServiceInterface {

    void deletePasswordToken(String token);

    Optional<PasswordToken> getToken(String token);

    String createNewToken(SiteUser user);

    String createPasswordToken(SiteUser user);

    void delete(User user);
}
