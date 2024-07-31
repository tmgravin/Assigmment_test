package com.msp.assignment.repository;

import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    Optional<Users> findByEmail(String email);
}
