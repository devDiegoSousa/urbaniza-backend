package com.urbaniza.authapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the input data provided for an operation
 * is invalid according to business rules, even though it may
 * have passed basic format validations (such as @NotNull, @Size).
 * For example, trying to set a status for a report that it already has. *
 * <p>This exception is automatically mapped to an HTTP 400 (Bad Request) response</p>
 *
 * <p><b>Usage Example (Service layer):</b></p>
 * <pre>{@code
 * public Report updateReportStatus(Long reportId, Status newStatus) {
 * Report report = reportRepository.findById(reportId)
 * .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + reportId));
 *
 * if (report.getStatus().equals(newStatus)) {
 * throw new InvalidInputException("Report already has status: " + newStatus.getName()); * }
 * report.setStatus(newStatus);
 * return reportRepository.save(report);
 * }
 * }</pre>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
  public InvalidInputException(String message) {
    super(message);
  }
}