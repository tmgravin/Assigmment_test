package com.msp.assignment.service.impl;

import com.msp.assignment.model.AccountDetails;
import com.msp.assignment.repository.AccountDetailsRepo;
import com.msp.assignment.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {
    @Autowired
    private AccountDetailsRepo accountDetailsRepo;

    @Override
    public Optional<AccountDetails> get(Long id) {
        return accountDetailsRepo.findById(id);
    }

    @Override
    public List<AccountDetails> getAll() {
        return accountDetailsRepo.findAll();
    }

    @Override
    public AccountDetails addAccountDetails(AccountDetails accountDetails) {
        return accountDetailsRepo.save(accountDetails);
    }
}
