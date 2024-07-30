package com.msp.assignment.repository;

import com.msp.assignment.model.UsersContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersContactRepo extends JpaRepository<UsersContact, Long> {
    @Query(value = "SELECT * from users_contact uc where uc.users_id = : userId", nativeQuery = true)
    List<UsersContact> findByUserId(@Param("user_id") Long userId);
}
