package com.urbaniza.authapi.dto.user;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequestDTO {

  @NotBlank(message = "Password is required to confirm deletion.")
  private String password;

  public DeleteUserRequestDTO() {}
  public DeleteUserRequestDTO(String password) {
    this.password = password;
  }

  public String getPassword() {return password;}
  public void setPassword(String password) {this.password = password;}
}
