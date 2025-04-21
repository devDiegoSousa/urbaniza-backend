package com.urbaniza.authapi.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.urbaniza.authapi.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

}
