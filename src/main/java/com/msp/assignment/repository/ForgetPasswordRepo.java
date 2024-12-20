package com.msp.assignment.repository;

import com.msp.assignment.model.ForgetPassword;
import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForgetPasswordRepo extends JpaRepository<ForgetPassword, Long> {
    Optional<ForgetPassword> findByCode(int code);

    Optional<ForgetPassword> findByIsVerified(String isVerified);

    @Query("SELECT fp FROM ForgetPassword fp WHERE fp.users.id = :userId ORDER BY fp.createdAt DESC")
    List<ForgetPassword> findAllCodesByUser(@Param("userId")Long userId);

    void deleteByUsersId(Long userId);
}
