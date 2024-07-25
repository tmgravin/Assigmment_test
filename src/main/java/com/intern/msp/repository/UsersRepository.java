package com.intern.msp.repository;

import com.intern.msp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
//    @Query("SELECT u FROM Users u WHERE u.email = :email")
//    List<Users> findBy(@Param("email") String email);
}
