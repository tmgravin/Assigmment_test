package com.intern.msp.controller;

import com.intern.msp.model.Users;
import com.intern.msp.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    public  UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping
    public Users createUser(@RequestBody Users user) {
            Users users = new Users();
            users.setName(user.getName());
            users.setPassword(user.getPassword());
            users.setEmail(user.getEmail());
            user.setUserType(user.getUserType());
            return usersService.createUser(user);
        }


    // Update an existing user
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable(name = "id") Long id, @RequestBody Users user) {
        return usersService.updateUser(id, user);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    // Get a user by email
    @GetMapping("/email")
    public Users getUserByEmail(@RequestParam String email) {
        return usersService.getUserByEmail(email);
    }

    // Get all users
    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

}
