package com.urbaniza.authapi.dto.user.update;

import jakarta.validation.constraints.NotBlank;

public class UpdateEmailRequestDTO {

    @NotBlank
    String email;

    public UpdateEmailRequestDTO() {}
    public UpdateEmailRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
