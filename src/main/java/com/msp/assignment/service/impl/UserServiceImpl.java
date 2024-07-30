package com.msp.assignment.service.impl;

import com.msp.assignment.controller.UsersController;
import com.msp.assignment.enumerated.LoginType;
import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.exception.EmailRelatedException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.ForgetPassword;
import com.msp.assignment.model.Users;
import com.msp.assignment.model.UsersVerification;
import com.msp.assignment.repository.ForgetPasswordRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.repository.UsersVerificationRepo;
import com.msp.assignment.service.UsersService;
import com.msp.assignment.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UsersService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UsersVerificationRepo usersVerificationRepo;

    @Autowired
    private ForgetPasswordRepo forgetPasswordRepo;

    @Autowired
    private MailUtils mailUtils;

    @Override
    @Transactional
    public String signupUser(Users users) {
        try {
            if (users.getPassword() == null || users.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }
            users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
            users.setIsEmailVerified('N');
            if (users.getUserType() == null) {
                users.setUserType(UserType.ASSIGNMENT_CREATOR);
            }
            if (users.getLoginType() == null) {
                users.setLoginType(LoginType.FACEBOOK);
            }
            String savedUser = String.valueOf(userRepository.save(users));

            sendVerificationEmail(users.getEmail());
            return savedUser;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void sendVerificationEmail(String email) {
        try {
            Users user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this email: " + email));
            if (user.getIsEmailVerified() == 'Y') {
                throw new EmailRelatedException("Email is already verified. Please proceed for login.");
            }

            String token = UUID.randomUUID().toString();
            String verificationLink = "http://localhost:8080/api/users/verifyEmail?token=" + token;

            UsersVerification tokenEntity = new UsersVerification();
            tokenEntity.setToken(token);
            tokenEntity.setExpiredAt(Timestamp.from(Instant.now().plusSeconds(21600)));
            tokenEntity.setUsers(user);
            usersVerificationRepo.save(tokenEntity);

            mailUtils.emailVerificationToken(email, user.getName(), verificationLink);
        } catch (ResourceNotFoundException | EmailRelatedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void verifyEmailToken(String token) {
        try {
            UsersVerification usersVerification = usersVerificationRepo.findByToken(token).orElseThrow(() -> new IllegalStateException("Invalid verification token."));

            List<UsersVerification> allTokens = usersVerificationRepo.findAllTokensByUser(usersVerification.getUsers().getId());
            if (allTokens.isEmpty()) {
                throw new IllegalStateException("No verification tokens found for user.");
            }

            UsersVerification latestToken = allTokens.get(0);
            if (!latestToken.getToken().equals(token)) {
                throw new IllegalStateException("This is not the latest verification token.");
            }
            if (latestToken.getExpiredAt().toInstant().isBefore(Instant.now())) {
                throw new IllegalStateException("Verification token expired.");
            }
            Users fetchUser = userRepository.findById(latestToken.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this id:" + latestToken.getUsers().getId()));
            fetchUser.setIsEmailVerified('Y');
            fetchUser.setUpdatedAt(Timestamp.from(Instant.now()));
            userRepository.save(fetchUser);

            usersVerificationRepo.deleteByUsersId(usersVerification.getUsers().getId());
        } catch (IllegalStateException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Users loginUser(String email, String password) {
        try {
            Users user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email don't match."));
            if (user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                if (user.getIsEmailVerified() == 'N') {
                    throw new IllegalStateException("User is not verified, please verify your email before login.");
                }
                return user;
            } else {
                throw new ResourceNotFoundException("Password don't match.");
            }
        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Object getAllUsers(Long id) {
        try {
            if (id != null) {
                Optional<Users> users = userRepository.findById(id);
                return users.orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
            } else {
                List<Users> users = userRepository.findAll();
                if (users.isEmpty()) {
                    throw new ResourceNotFoundException("User list is null.");
                }
                return users;
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public Users updateUser(Long id, Users user) {
        try {
            Users existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            existingUser.setUpdatedAt(Timestamp.from(Instant.now()));
            return userRepository.save(existingUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new ResourceNotFoundException("User not found with id: " + id);
            }
            userRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void forgetPassword(String email) {
        try {
            Users users = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this email: " + email));
            if (users.getIsEmailVerified() == 'N') {
                throw new EmailRelatedException("Email is not verified. Please verify your email before requesting for resetting password,");
            }
            UUID uuid = UUID.randomUUID();
            long lsb = uuid.getLeastSignificantBits();
            int verificationCode = Math.abs((int) (lsb % 1000000));

            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setCode(verificationCode);
            forgetPassword.setIsVerified("N");
            forgetPassword.setUsers(users);
            forgetPassword.setExpiredAt(Timestamp.from(Instant.now().plusSeconds(3600)));

            // Debugging log statements
            System.out.println("Generated code: " + verificationCode);
            System.out.println("Setting user: " + users.getId());

            forgetPasswordRepo.save(forgetPassword);

            mailUtils.forgetPasswordVerificationCode(email, users.getName(), verificationCode);
        } catch (EmailRelatedException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void verifyPasswordResetCode(int verificationCode) {
        try {
            ForgetPassword forgetPassword = forgetPasswordRepo.findByCode(verificationCode).orElseThrow(() -> new IllegalStateException("Invalid password verification code."));

            List<ForgetPassword> allCodes = forgetPasswordRepo.findAllCodesByUser(forgetPassword.getUsers().getId());

            if (allCodes.isEmpty()) {
                throw new IllegalStateException("No verification codes found for user.");
            }
            ForgetPassword latestCode = allCodes.get(0);
            if (latestCode.getCode() != verificationCode) {
                throw new IllegalStateException("This is not the latest password reset code.");
            }
            if (latestCode.getExpiredAt().toInstant().isBefore(Instant.now())) {
                throw new IllegalStateException("Password verification code expired.");
            }

            Users users = userRepository.findById(forgetPassword.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with Id: " + forgetPassword.getUsers().getId()));
            latestCode.setIsVerified("Y");
            forgetPasswordRepo.save(latestCode);
            userRepository.save(users);

        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String newPassword, String confirmPassword) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Password mismatch. New password and confirm password must be same.");
            }
            ForgetPassword forgetPassword = forgetPasswordRepo.findByIsVerified("Y").orElseThrow(() -> new ResourceNotFoundException("Password reset code is not verified."));
            Users users = userRepository.findById(forgetPassword.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this Id."));
            users.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            userRepository.save(users);

            forgetPasswordRepo.deleteById(forgetPassword.getUsers().getId());
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

}
