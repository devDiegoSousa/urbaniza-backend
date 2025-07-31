package com.urbaniza.authapi.dto.user;

public class UpdateEmailRequestDTO {

  String email;

  public UpdateEmailRequestDTO() {}
  public UpdateEmailRequestDTO(String email){
    this.email = email;
  }

  public String getEmail() {return email;}
  public void setEmail(String email) {this.email = email;}
}
