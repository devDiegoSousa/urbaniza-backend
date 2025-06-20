package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.dto.user.UpdateProfileRequestDTO;
import com.urbaniza.authapi.dto.user.UpdateProfileResponseDTO;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  UserService userService;

  @PutMapping("/profile/edit")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> updateProfile(
      @Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO,
      @AuthenticationPrincipal User authenticatedUser){

    UpdateProfileResponseDTO updatedProfile = userService.updateProfile(updateProfileRequestDTO, authenticatedUser.getEmail());
    return ResponseEntity.ok(updatedProfile);
  }

}
