package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.Payments;
import com.msp.assignment.model.SessionUsers;
import com.msp.assignment.model.Users;
import com.msp.assignment.repository.PaymentRepo;
import com.msp.assignment.repository.SessionUserRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private SessionUserRepo sessionUserRepo;
    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    @Transactional
    public Object getAllUsers(Long id) {
        try {
            if (id != null) {
                Optional<Users> usersOptional = usersRepository.findById(id);
                return usersOptional.orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
            } else {
                List<Users> users = usersRepository.findAll();
                if (users.isEmpty()) {
                    throw new ResourceNotFoundException("User list is empty.");
                }
                return users;
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<Users> getUsersByRole(UserType userType) {
        try {
            List<Users> users = usersRepository.findByUserType(userType);
            if (users.isEmpty()) {
               throw new ResourceNotFoundException("Users list is empty.");
            }
            return users;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public Integer countUsersByUserType(UserType userType) {
        try {
            return usersRepository.countUsersByUserType(userType);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
    
    // added by ram kumar
    
	@Override
	public List<SessionUsers> getSessionsByStatus(String status)
	{
		if(status.compareToIgnoreCase("login") == 0)
			return sessionUserRepo.getLoggedInUsers();
		else if(status.compareToIgnoreCase("logout") == 0)
			return sessionUserRepo.getLoggedOutUsers();
		else
			return sessionUserRepo.findAll();
	}

    @Override
    public Payments approvePayment(Long id) {
        Payments payments=paymentRepo.findById(id).get();
        if(payments == null){
            throw new RuntimeException("The Payment does not exist");
        }
       payments.setIsPaymentVerified("Y");
        return payments;
    }
}
