package com.urbaniza.authapi.dto.user.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequestDTO {
    @NotBlank
    @Size(min = 6)
    String password;
    @NotBlank
    @Size(min = 6)
    String newPassword;

    public UpdatePasswordRequestDTO() {}
    public UpdatePasswordRequestDTO(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getNewPassword() {return newPassword;}
    public void setNewPassword(String newPassword) {this.newPassword = newPassword;}
}
