package com.urbaniza.authapi.security;

import com.urbaniza.authapi.enums.UserRole;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component
public class AdminInitializer implements CommandLineRunner {

  @Value("${urbaniza.app.admin.email}")
  private String adminEmail;
  @Value("${urbaniza.app.admin.password}")
  private String adminPassword;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

  @Override
  public void run(String... args) {
    try {

      Optional<User> found = userRepository.findByEmail(adminEmail);
      if (found.isPresent()) {
        return;
      }

      User admin = new User();
      admin.setEmail(adminEmail);
      admin.setPassword(passwordEncoder.encode(adminPassword));
      admin.setFirstName("Admin");
      admin.setLastName("Urbaniza");
      admin.setRole(UserRole.ADMIN);

      userRepository.save(admin);

    } catch (Exception e) {
      System.out.println("admin user not created:");
      e.printStackTrace();
    }
  }
}
