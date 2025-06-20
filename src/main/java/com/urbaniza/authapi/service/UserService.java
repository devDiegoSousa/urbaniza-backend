package com.urbaniza.authapi.service;

import com.urbaniza.authapi.dto.user.UpdateProfileRequestDTO;
import com.urbaniza.authapi.dto.user.UpdateProfileResponseDTO;
import com.urbaniza.authapi.exception.InvalidInputException;
import com.urbaniza.authapi.exception.ResourceNotFoundException;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

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

  private UpdateProfileResponseDTO convertToEditProfileResponseDTO(User user) {
    UpdateProfileResponseDTO responseDTO = new UpdateProfileResponseDTO();
    responseDTO.setFirstName(user.getFirstName());
    responseDTO.setLastName(user.getLastName());

    return responseDTO;
  }
}
