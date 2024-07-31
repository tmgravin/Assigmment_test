package com.msp.assignment.controller;

import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.Users;
import com.msp.assignment.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private AdminService adminService;

    //Api for get users by id and get all users
    @GetMapping("/")
    public ResponseEntity<?> getUsers(@RequestParam(name = "id", required = false) Long id) {
        log.info("Inside getUsers method of AdminController.");
        try {
            Object users = adminService.getAllUsers(id);
            return ResponseEntity.ok(users);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for getting users by role
    @GetMapping("/role")
    public ResponseEntity<?> getUsersByRole(@RequestParam(name = "userType") UserType userType) {
        log.info("Inside getUsersByRole method of AdminController");
        try {
            List<Users> users = adminService.getUsersByRole(userType);
            return ResponseEntity.ok(users);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
