package com.urbaniza.authapi.dto.user;

public class UpdateProfileRequestDTO {

  private String firstName;
  private String lastName;

  public UpdateProfileRequestDTO() {}
  public UpdateProfileRequestDTO(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {return firstName;}
  public void setFirstName(String firstName) {this.firstName = firstName;}

  public String getLastName() {return lastName;}
  public void setLastName(String lastName) {this.lastName = lastName;}
}
