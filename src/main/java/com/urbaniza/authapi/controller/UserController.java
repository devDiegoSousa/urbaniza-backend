package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.dto.user.*;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.service.EmailService;
import com.urbaniza.authapi.service.UserService;
import com.urbaniza.authapi.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  UserService userService;
  @Autowired
  EmailService emailService;
  @Autowired
  JwtUtils jwtUtils;

  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> viewProfile(@AuthenticationPrincipal User authenticatedUser) {

    ViewProfileResponseDTO profile = userService.viewProfile(authenticatedUser.getEmail());
    return ResponseEntity.ok(profile);
  }

  @PutMapping("/profile/update")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> updateProfile(
      @Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO,
      @AuthenticationPrincipal User authenticatedUser){

    UpdateProfileResponseDTO updatedProfile = userService.updateProfile(updateProfileRequestDTO, authenticatedUser.getEmail());
    return ResponseEntity.ok(updatedProfile);
  }

  @PutMapping("/profile/update/email")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> updateEmail(
      @Valid @RequestBody UpdateEmailRequestDTO updateEmailRequestDTO,
      @AuthenticationPrincipal User authenticatedUser ) {

    String email = updateEmailRequestDTO.getEmail();

    String confirmationToken = jwtUtils.generateConfirmEmailToken(email);

    emailService.sendConfirmationEmail(updateEmailRequestDTO.getEmail(), confirmationToken);
    return ResponseEntity.ok("email sent successfully: Confirm your email");
  }

  @DeleteMapping("/delete")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> deleteUser(
      @Valid @RequestBody DeleteUserRequestDTO deleteUserDTO,
      @AuthenticationPrincipal User authenticatedUser) {

    userService.deleteUser(deleteUserDTO, authenticatedUser.getEmail());
    return  ResponseEntity.ok("user permanently deleted");
  }

}
