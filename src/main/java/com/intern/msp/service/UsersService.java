package com.intern.msp.service;

import com.intern.msp.model.Users;
import com.intern.msp.model.UsersVerification;

import java.util.List;

public interface UsersService {
    Users signup(Users user);
    Users getUserById(Long id);
    Users getUserByEmail(String email);
    List<Users> getAllUsers();
    Users updateUser(Long id, Users user);
    void deleteUser(Long id);
    Users login(String email, String password);
}
