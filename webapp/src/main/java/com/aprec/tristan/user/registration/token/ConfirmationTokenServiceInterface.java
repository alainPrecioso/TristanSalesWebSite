package com.aprec.tristan.user.registration.token;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.User;

import java.util.Optional;

public interface ConfirmationTokenServiceInterface {

    Optional<ConfirmationToken> getToken(String token);

    int setConfirmedAt(String token);

    String refreshConfirmationToken(SiteUser user);

    String createConfirmationToken(SiteUser user);

    void delete(User user);
}
