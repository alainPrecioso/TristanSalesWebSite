package com.aprec.webapp.user.token;

import com.aprec.webapp.user.entities.PasswordToken;
import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.entities.User;
import com.aprec.webapp.user.repositories.PasswordTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    public PasswordTokenServiceImpl(PasswordTokenRepository passwordTokenRepository) {
        super();
        this.passwordTokenRepository = passwordTokenRepository;
    }

    private void savePasswordToken(PasswordToken passwordToken) {
        passwordTokenRepository.save(passwordToken);
    }

    public void deletePasswordToken(String token) {
        passwordTokenRepository.deleteByToken(token);
    }

    public Optional<PasswordToken> getToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public String createNewToken(SiteUser user) {
        String newToken = UUID.randomUUID().toString();
        passwordTokenRepository
                .updateToken(passwordTokenRepository.findByUser(user).get().getToken(),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        newToken);
        return newToken;
    }

    public String createPasswordToken(SiteUser user) {
        PasswordToken passwordToken = new PasswordToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);

        savePasswordToken(passwordToken);
        return passwordToken.getToken();
    }

    public void delete(User user) {
        passwordTokenRepository.findByUser(user).ifPresent(passwordTokenRepository::delete);
    }
}
