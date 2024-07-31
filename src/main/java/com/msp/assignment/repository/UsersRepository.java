package com.msp.assignment.repository;

import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    Optional<Users> findByEmail(String email);

    List<Users> findByUserType(UserType userType);
}
