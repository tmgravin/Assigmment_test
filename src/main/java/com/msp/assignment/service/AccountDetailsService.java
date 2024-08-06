package com.msp.assignment.service;

import com.msp.assignment.model.AccountDetails;

import java.util.List;
import java.util.Optional;

public interface AccountDetailsService {
    Object getAccountDetails(Long id);

    AccountDetails addAccountDetails(AccountDetails accountDetails);
}
