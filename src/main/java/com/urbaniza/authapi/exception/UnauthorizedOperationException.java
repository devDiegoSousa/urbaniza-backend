package com.urbaniza.authapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an authenticated user tries to perform an operation
 * for which they do not have the necessary permissions (not authorized).
 * Unlike an authentication failure (401), here the user is authenticated,
 * but is not authorized for the specific action.
 *
 * <p>This exception is automatically mapped to an HTTP UNAUTHORIZED response.</p>
 *
 * <p><b>Example of Usage (Service layer):</b></p>
 * <pre>{@code
 * public void deleteReport(Long postId, User currentUser) {
 * Report report = reportRepository.findById(postId)
 * .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + postId)); *
 * if (!post.getAuthor().getId().equals(currentUser.getId()) && currentUser.getRole() != UserRole.ADMIN) {
 * throw new UnauthorizedOperationException("User not authorized to delete this report.");
 * }
 * reportRepository.delete(post);
 * }
 * }</pre>
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedOperationException extends RuntimeException {
  public UnauthorizedOperationException(String message) {
    super(message);
  }
}