package com.urbaniza.authapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; // Alterado para JpaRepository
import org.springframework.stereotype.Repository;
import com.urbaniza.authapi.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> { // Estende JpaRepository e usa Long

    Optional<Token> findByToken(String token);
}
