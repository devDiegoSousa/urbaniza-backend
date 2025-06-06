package com.urbaniza.authapi.dto.report;

public class ReporterInfoDTO {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;

  // Construtors
  public ReporterInfoDTO() {}
  public ReporterInfoDTO(Long userId, String firstName, String lastName, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // Getters e Setters
  public Long getUserId() {return userId;}
  public void setUserId(Long userId) {this.userId = userId;}

  public String getFirstName() {return firstName;}
  public void setFirstName(String firstName) {this.firstName = firstName;}

  public String getLastName() {return lastName;}
  public void setLastName(String lastName) {this.lastName = lastName;}

  public String getEmail() {return email;}
  public void setEmail(String email) {this.email = email;}

  // toString
  @Override
  public String toString() {
    return "ReporterInfoDTO{" +
        "userId=" + userId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}