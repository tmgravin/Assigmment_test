package com.msp.assignment.repository;

import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
//    @Query("SELECT u FROM Users u WHERE u.email = :email")
//    List<Users> findBy(@Param("email") String email);
}
