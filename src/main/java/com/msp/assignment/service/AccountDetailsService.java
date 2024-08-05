package com.msp.assignment.service;

import com.msp.assignment.model.AccountDetails;

import java.util.List;
import java.util.Optional;

public interface AccountDetailsService {
    Optional<AccountDetails> get(Long id);
    List<AccountDetails> getAll();

    AccountDetails addAccountDetails(AccountDetails accountDetails);
}
