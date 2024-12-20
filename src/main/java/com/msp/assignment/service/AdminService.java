package com.msp.assignment.service;

import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.model.Payments;
import com.msp.assignment.model.SessionUsers;
import com.msp.assignment.model.Users;

import java.util.List;

public interface AdminService {
    Object getAllUsers(Long id);

    List<Users> getUsersByRole(UserType userType);

    Integer countUsersByUserType(UserType userType);
    
    List<SessionUsers> getSessionsByStatus(String status);
    Payments approvePayment(Long id);

}
