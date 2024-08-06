package com.msp.assignment.repository;

import com.msp.assignment.model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, Long> {

    List<AccountDetails> findByUsersId(Long userId);
}
