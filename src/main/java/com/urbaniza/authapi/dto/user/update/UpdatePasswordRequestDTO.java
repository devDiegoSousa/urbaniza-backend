package com.urbaniza.authapi.dto.user.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequestDTO {
    @NotBlank
    @Size(min = 6)
    String password;
    @NotBlank
    @Size(min = 6)
    String newPassowrd;

    public UpdatePasswordRequestDTO() {}
    public UpdatePasswordRequestDTO(String password, String newPassowrd) {
        this.password = password;
        this.newPassowrd = newPassowrd;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getNewPassowrd() {return newPassowrd;}
    public void setNewPassowrd(String newPassowrd) {this.newPassowrd = newPassowrd;}
}
