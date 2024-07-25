package com.intern.msp.controller;

import com.intern.msp.model.Users;
import com.intern.msp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    //    @PostMapping("/signup")
//    public Users signUp(@RequestBody Users user) {
//        return usersService.signup(user);
//    }
//
//    @PostMapping("/login")
//    public Users login(@RequestBody Users user) {
//        return usersService.login(user.getEmail(), user.getPassword());
//    }
//
//    // Update an existing user
//    @PutMapping("/{id}")
//    public Users updateUser(@PathVariable(name = "id") Long id, @RequestBody Users user) {
//        return usersService.updateUser(id, user);
//    }
//
//    // Get a user by ID
//    @GetMapping("/{id}")
//    public Users getUserById(@PathVariable Long id) {
//        return usersService.getUserById(id);
//    }
//
//    // Get a user by email
//    @GetMapping("/email")
//    public Users getUserByEmail(@RequestParam String email) {
//        return usersService.getUserByEmail(email);
//    }
//
//    // Get all users
//    @GetMapping("/allusers")
//    public List<Users> getAllUsers() {
//        return usersService.getAllUsers();
//    }
//
//    // Delete a user by ID
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        usersService.deleteUser(id);
//    }
//}
    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@Valid @RequestBody Users user) {
        Users createdUser = usersService.signup(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestParam String email, @RequestParam String password) {
        Users user = usersService.login(email, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users user) {
        Users updatedUser = usersService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<Users> getUserByEmail(@RequestParam String email) {
        Users user = usersService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


