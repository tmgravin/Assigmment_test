package com.msp.assignment.repository;

import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = {"usersContacts"})
    Optional<Users> findById(Long id);

    @EntityGraph(attributePaths = {"usersContacts"})
    List<Users> findAll();

    Optional<Users> findByEmail(String email);
}
