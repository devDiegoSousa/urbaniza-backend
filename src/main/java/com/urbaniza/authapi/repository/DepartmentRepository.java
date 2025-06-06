package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
  Optional<Department> findByEmail(String email);
}
