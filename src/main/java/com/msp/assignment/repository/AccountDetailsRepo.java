package com.msp.assignment.repository;

import com.msp.assignment.model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, Long> {
}
