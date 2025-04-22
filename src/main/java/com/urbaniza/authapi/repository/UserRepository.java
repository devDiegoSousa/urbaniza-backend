package com.urbaniza.authapi.repository;

import java.util.Optional;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.urbaniza.authapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
}