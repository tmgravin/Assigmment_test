package com.msp.assignment.service;

import com.msp.assignment.model.Users;

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
