package com.msp.assignment.service.impl;

import com.msp.assignment.exception.ResourceConflictException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.AccountDetails;
import com.msp.assignment.repository.AccountDetailsRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

    @Autowired
    private AccountDetailsRepo accountDetailsRepo;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<AccountDetails> getAccountDetails(Long userId) {
        try {
            if(userId != null) {
                List<AccountDetails> accountDetails = accountDetailsRepo.findByUsersId(userId);
                if (accountDetails.isEmpty()) {
                    throw new ResourceNotFoundException("Account details not found for this account id: " + userId);
                }
                return accountDetails;
            }else{
                return accountDetailsRepo.findAll();
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public AccountDetails addAccountDetails(AccountDetails accountDetails) {
        Long userId = accountDetails.getUsers().getId();
        try {
            if (!usersRepository.existsById(userId)) {
                throw new ResourceNotFoundException("User with Id " + userId + "does not exist.");
            }
            List<AccountDetails> existingDetails = accountDetailsRepo.findByUsersId(userId);
            if (!existingDetails.isEmpty()) {
                throw new ResourceConflictException("Account details for this user already exist.");
            }
            return accountDetailsRepo.save(accountDetails);
        } catch (ResourceNotFoundException | ResourceConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
