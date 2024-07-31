package com.msp.assignment.service;

import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.model.Users;

import java.util.List;

public interface AdminService {
    Object getAllUsers(Long id);

    List<Users> getUsersByRole(UserType userType);
}
