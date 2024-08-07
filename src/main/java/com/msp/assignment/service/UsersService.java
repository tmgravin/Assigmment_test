package com.msp.assignment.service;

import com.msp.assignment.dto.UsersDto;
import com.msp.assignment.model.Users;

import java.util.Optional;

public interface UsersService {
    String signupUser(Users user);

    void sendVerificationEmail(String email);

    void verifyEmailToken(String token);

    Users loginUser(String email, String password);

    Optional<Users> getUser(Long id);

    Users updateUser(Long id, Users user);

    void deleteUser(Long id);

    void forgetPassword(String email);

    void verifyPasswordResetCode(int verificationCode);

    void resetPassword(String Password);

    void changePassword(UsersDto usersDto);
    
    Users getUserByEmail(String email);
}
