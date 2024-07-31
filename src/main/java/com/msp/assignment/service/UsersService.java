package com.msp.assignment.service;

import com.msp.assignment.model.Users;

public interface UsersService {
    String signupUser(Users user);

    void sendVerificationEmail(String email);

    void verifyEmailToken(String token);

    Users loginUser(String email, String password);

    Object getAllUsers(Long id);

    Users updateUser(Long id, Users user);

    void deleteUser(Long id);

    void forgetPassword(String email);

    void verifyPasswordResetCode(int verificationCode);

    void resetPassword(String newPassword, String confirmPassword);
}
