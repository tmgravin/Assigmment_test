package com.msp.assignment.config;

import com.msp.assignment.model.Users;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUtils {
    private static final Map<String, Users> loggedInUsers = new HashMap<>();

    public static void addLoggedInUser(String email, Users user) {
        loggedInUsers.put(email, user);
    }

    public static void removeLoggedInUser(String email) {
        loggedInUsers.remove(email);
    }

    public static Users getLoggedInUser(String email) {
        return loggedInUsers.get(email);
    }

    public static Users geUserInfo() //getCurrentLoggedInUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return getLoggedInUser(email);
        }
        return null;
    }
}