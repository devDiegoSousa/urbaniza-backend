package com.urbaniza.authapi.security;

import com.urbaniza.authapi.enums.UserRole;
import com.urbaniza.authapi.model.Department;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.DepartmentRepository;
import com.urbaniza.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DepartmentUsersInitializer implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private DepartmentRepository departmentRepository;

  public void run(String... args){
    try {

      System.out.println("[INFO] Starting to create DEPARTMENT users for testing");


      List<Department> departments = departmentRepository.findAll();

      if (departments.isEmpty()) {
        System.out.println("[ERROR] No departments registered. Users cannot be created");
        return;
      }

      System.out.println("Iniciando a criação de usuários para departamentos...");

      for (Department department : departments) {
        String departmentEmail = "secretaria." + department.getName().toLowerCase().replaceAll("\\s+", "") + "@urbaniza.com";
        String defaultPassword = "dept123";

        Optional<User> existingUser = userRepository.findByEmail(departmentEmail);
        if (existingUser.isPresent()) {
          System.out.println("This employee of department '" + department.getName() + "' already exists: " + departmentEmail);
          continue;
        }

        User departmentUser = new User();
        departmentUser.setFirstName("Funcionario");
        departmentUser.setLastName(department.getName());
        departmentUser.setEmail(departmentEmail);
        departmentUser.setPassword(passwordEncoder.encode(defaultPassword));
        departmentUser.setRole(UserRole.DEPARTMENT);
        departmentUser.setDepartmentId(department.getId());

        userRepository.save(departmentUser);
      }
    } catch (Exception e) {
      System.out.println("department users cannot be created:");
      e.printStackTrace();
    }

    System.out.println("[INFO] Starting to create DEPARTMENT users for testing");

  }
}
