package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.service.EmailService;
import com.urbaniza.authapi.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/email")
public class EmailController {

  @Autowired
  EmailService emailService;
  @Autowired
  JwtUtils jwtUtils;

  @GetMapping("/password-recovery")
  public ResponseEntity<?> confirmEmail(@AuthenticationPrincipal User authenticatedUser) {

    Random randomNumber = new Random();
    String randomString = randomNumber.toString();

    String passwordRecoveryToken = jwtUtils.generatePasswordRecoveryToken(randomString);
    emailService.sendRecoveryPasswordEmail(authenticatedUser.getEmail(), passwordRecoveryToken);

    return ResponseEntity.ok("email sent");
  }
}
