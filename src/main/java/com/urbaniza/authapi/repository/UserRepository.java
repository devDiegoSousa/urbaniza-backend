package com.urbaniza.authapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; // Alterado para JpaRepository
import org.springframework.stereotype.Repository;
import com.urbaniza.authapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // Checks if a user exists with the provided email.
}
