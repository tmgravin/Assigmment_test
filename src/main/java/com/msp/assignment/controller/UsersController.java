package com.msp.assignment.controller;

import com.msp.assignment.dto.UsersDto;
import com.msp.assignment.exception.EmailRelatedException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.ForgetPassword;
import com.msp.assignment.model.Users;
import com.msp.assignment.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService userService;

    //Api for get users by id
    @GetMapping("/")
    public ResponseEntity<?> getUsers(@RequestParam(name = "id") Long id) {
        log.info("Inside getUsers method of UserController.");
        try {
            Optional<Users> user = userService.getUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for user signup and sending email verification email
    @PostMapping("/")
    public ResponseEntity<String> signupUser(@RequestBody Users user, HttpSession session) {
        log.info("Inside signupUser method of UserController.");
        try {
            userService.signupUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User signup successfully. Please verify your email for login.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users users) {
        log.info("Inside loginUser method of UserController.");
        try {
            users = userService.loginUser(users.getEmail(), users.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (ResourceNotFoundException e) {
            log.error("Resource not found: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            log.error("Runtime exception: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for requesting email verification again
    @PostMapping("/requestEmailToken")
    public ResponseEntity<String> resendVerificationEmail(@RequestBody Users user) {
        log.info("Inside resendVerificationEmail method of UserController.");
        try {
            userService.sendVerificationEmail(user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Email verification token is send again successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailRelatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for verifying email token
    @GetMapping("/verifyEmail")
    public ModelAndView verifyEmailToken(@RequestParam("token") String verificationToken) {
        log.info("Inside verifyEmailToken method of UserController.");
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.verifyEmailToken(verificationToken);
            modelAndView.setViewName("Assignment_verifySuccess");
        } catch (IllegalStateException e) {
            log.error("Verification token expired.", e);
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (ResourceNotFoundException e) {
            log.error("User doesn't exist with this email.", e);
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (IllegalArgumentException e) {
            log.error("Verification process failed.", e);
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (Exception e) {
            log.error("Internal server error.", e);
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        }
        return modelAndView;
    }

    //Api for updating user
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users user) {
        log.info("Inside updateUser method of UserController.");
        try {
            Users updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for deleting user
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Inside deleteUser method of UserController.");
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for forget password and sending reset code
    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Users users) {
        log.info("Inside forgetPassword method of UserController.");
        try {
            userService.forgetPassword(users.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Password verification code is send to your email.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailRelatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for verifying password reset code
    @PostMapping("/verifyResetCode")
    public ResponseEntity<String> verifyPasswordResetCode(@RequestBody ForgetPassword forgetPassword) {
        log.info("Inside verifyPasswordResetCode method of UserController.");
        try {
            userService.verifyPasswordResetCode(forgetPassword.getCode());
            return ResponseEntity.status(HttpStatus.OK).body("Password Reset code verified successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    //Api for resetting password
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Users user) {
        log.info("Inside resetPassword method of UserController.");
        try {
            userService.resetPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for changing password
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UsersDto usersDto){
        log.info("Inside changePassword method of UserController.");
        try{
            userService.changePassword(usersDto);
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}


