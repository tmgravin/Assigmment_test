package com.msp.assignment.service.impl;

import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.AccountDetails;
import com.msp.assignment.repository.AccountDetailsRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

    @Autowired
    private AccountDetailsRepo accountDetailsRepo;


    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Object getAccountDetails(Long id) {
        try {
            if(id != null){
                Optional<AccountDetails> accountDetail = accountDetailsRepo.findById(id);
                return accountDetail.orElseThrow(()-> new ResourceNotFoundException("Account details not found for this account id: " +id));
            }else{
                List<AccountDetails> accountDetails = accountDetailsRepo.findAll();
                if(accountDetails.isEmpty()){
                    throw new ResourceNotFoundException("Account details list is empty.");
                }
                return accountDetails;
            }
        }catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public AccountDetails addAccountDetails(AccountDetails accountDetails) {
        Long userId = accountDetails.getUsers().getId();
        if(!usersRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with Id "+ userId + "does not exist.");
        }
        //Check if the user already has account details
        List<AccountDetails> existingDetails = accountDetailsRepo.findByUsersId(userId);
        if(!existingDetails.isEmpty()){
            throw new ResourceNotFoundException("Account details for this user already exist.");
        }
        return accountDetailsRepo.save(accountDetails);
    }
}
