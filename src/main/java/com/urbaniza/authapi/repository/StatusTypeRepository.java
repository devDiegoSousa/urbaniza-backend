package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusTypeRepository extends JpaRepository<StatusType, Long> {

  Optional<StatusType> findByNameIgnoreCase(String name);
}
