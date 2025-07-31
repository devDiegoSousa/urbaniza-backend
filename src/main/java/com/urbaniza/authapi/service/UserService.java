package com.urbaniza.authapi.service;

import com.urbaniza.authapi.dto.user.*;
import com.urbaniza.authapi.dto.user.update.UpdateEmailRequestDTO;
import com.urbaniza.authapi.dto.user.update.UpdatePasswordRequestDTO;
import com.urbaniza.authapi.dto.user.update.UpdateProfileRequestDTO;
import com.urbaniza.authapi.dto.user.update.UpdateProfileResponseDTO;
import com.urbaniza.authapi.exception.InvalidInputException;
import com.urbaniza.authapi.exception.ResourceNotFoundException;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.UserRepository;
import com.urbaniza.authapi.util.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  EmailService emailService;
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public ViewProfileResponseDTO viewProfile(String authenticatedUserEmail) {
    Optional<User> user = userRepository.findByEmail(authenticatedUserEmail);

    if(user.isEmpty()) {
      throw new ResourceNotFoundException("User not found with email: " + authenticatedUserEmail);
    }

    ViewProfileResponseDTO profile = new ViewProfileResponseDTO();

    profile.setFirstName(user.get().getFirstName());
    profile.setLastName(user.get().getLastName());
    profile.setEmail(user.get().getEmail());

    return profile;
  }

  @Transactional
  public UpdateProfileResponseDTO updateProfile(UpdateProfileRequestDTO editProfileDTO, String authenticatedUserEmail){
    User userToUpdate = userRepository.findByEmail(authenticatedUserEmail).
                orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authenticatedUserEmail));

    if(editProfileDTO.getFirstName() == null && editProfileDTO.getLastName() == null) {
      throw new InvalidInputException("No changes to profile information were requested");
    }

    if(editProfileDTO.getFirstName() != null &&
        !Objects.equals(userToUpdate.getFirstName(), editProfileDTO.getFirstName())) {
      userToUpdate.setFirstName(editProfileDTO.getFirstName());
    }
    if(editProfileDTO.getLastName() != null &&
        !Objects.equals(userToUpdate.getLastName(), editProfileDTO.getLastName())) {
      userToUpdate.setLastName(editProfileDTO.getLastName());
    }

    User updatedUser = userRepository.save(userToUpdate);
    return convertToEditProfileResponseDTO(updatedUser);
  }

  @Transactional
  public void updateEmail(UpdateEmailRequestDTO updateEmailRequestDTO, String authenticatedUserEmail){
    User userFound = userRepository.findByEmail(authenticatedUserEmail).
            orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authenticatedUserEmail));

    if(updateEmailRequestDTO.getEmail() == userFound.getEmail()){
      throw new InvalidInputException("You already use this email");
    }

    if(userRepository.existsByEmail(updateEmailRequestDTO.getEmail())) {
      throw new InvalidInputException("Email already in use");
    }

    String confirmationToken = jwtUtils.generateConfirmEmailToken(updateEmailRequestDTO.getEmail());

    userFound.setConfirmationToken(confirmationToken);
    userFound.setEmailConfirmed(false);

    emailService.sendConfirmationEmail(updateEmailRequestDTO.getEmail(), confirmationToken);

    userFound.setEmail(updateEmailRequestDTO.getEmail());

    User updatedUser = userRepository.save(userFound);
  }

  @Transactional
  public void updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO, String authenticatedUserEmail) {
    User userFound = userRepository.findByEmail(authenticatedUserEmail).
            orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authenticatedUserEmail));

    if(userFound.getPassword() == updatePasswordRequestDTO.getNewPassword()){
      throw new InvalidInputException("Password already in use");
    };

    User updatedUser = userFound;
    updatedUser.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));

    userRepository.save(updatedUser);
  }

  @Transactional
  public void  deleteUser(DeleteUserRequestDTO deleteUserDTO, String authenticatedUserEmail) {
    Optional<User> userFound = userRepository.findByEmail(authenticatedUserEmail);

    if(userFound.isEmpty()){
      throw new ResourceNotFoundException("User not found with email: " + authenticatedUserEmail);
    }

    if (!passwordEncoder.matches(deleteUserDTO.getPassword(), userFound.get().getPassword())) {
      throw new IllegalArgumentException("Unable to delete user.");
    }

    userRepository.deleteByEmail(authenticatedUserEmail);
  }

  private UpdateProfileResponseDTO convertToEditProfileResponseDTO(User user) {
    UpdateProfileResponseDTO responseDTO = new UpdateProfileResponseDTO();
    responseDTO.setFirstName(user.getFirstName());
    responseDTO.setLastName(user.getLastName());

    return responseDTO;
  }
}
