package com.urbaniza.authapi.dto.user.update;

public class UpdateProfileResponseDTO {

  private String firstName;
  private String lastName;

  public UpdateProfileResponseDTO() {}
  public UpdateProfileResponseDTO(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {return firstName;}
  public void setFirstName(String firstName) {this.firstName = firstName;}

  public String getLastName() {return lastName;}
  public void setLastName(String lastName) {this.lastName = lastName;}
}
