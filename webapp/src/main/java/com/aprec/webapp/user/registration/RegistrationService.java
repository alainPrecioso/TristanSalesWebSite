package com.aprec.webapp.user.registration;

public interface RegistrationService {

    String register(RegistrationRequest request);

    void resendConfirmationMail(String username);

    String confirmToken(String token);

    String confirmPasswordToken(PasswordRequest request);

    String requestNewPassword(String email);

    boolean checkPassword(String username, String password);

    void resendConfirmationMailFromToken(String token);
}
