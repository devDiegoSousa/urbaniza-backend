package com.urbaniza.authapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a specific resource is not found in the system.
 * For example, when trying to search for a user by an ID that does not exist.
 *
 * <p>This exception is automatically mapped to an HTTP 404 (Not Found) response.</p>
 *
 * <p><b>Example of usage (Service layer):</b></p>
 * <pre>{@code
 * public User findUserById(Long userId) {
 * return userRepository.findById(userId)
 * .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
 * }
 * }</pre>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}