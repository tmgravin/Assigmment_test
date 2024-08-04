package com.msp.assignment.config;

import com.msp.assignment.model.Users;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUtils {
    private static final Map<String, Users> loggedInUsers = new ConcurrentHashMap();

    public static void addUser(String sessionId, Users user) {
        loggedInUsers.put(sessionId, user);
    }

    public static Users getUser(String sessionId) {
        return loggedInUsers.get(sessionId);
    }

    public static void removeUser(String sessionId) {
        loggedInUsers.remove(sessionId);
    }

    public static void clear() {
        loggedInUsers.clear();
    }
}