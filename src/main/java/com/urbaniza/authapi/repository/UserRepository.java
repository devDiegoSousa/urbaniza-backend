package com.urbaniza.authapi.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository; // Alterado para JpaRepository
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.urbaniza.authapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmationToken(String token);
    boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query("delete from User u where u.email = ?1")
    void deleteByEmail(String email);
}
