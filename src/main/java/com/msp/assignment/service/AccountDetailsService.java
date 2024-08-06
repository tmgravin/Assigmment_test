package com.msp.assignment.service;

import com.msp.assignment.model.AccountDetails;

import java.util.List;

public interface AccountDetailsService {
    List<AccountDetails> getAccountDetails(Long userId);

    AccountDetails addAccountDetails(AccountDetails accountDetails);
}
