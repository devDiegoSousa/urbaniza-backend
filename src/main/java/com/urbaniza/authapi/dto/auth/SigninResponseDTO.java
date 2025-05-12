package com.urbaniza.authapi.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SigninResponseDTO {

    @NotBlank(message = "Token is required")
    private String token;

    @NotNull(message = "Expiration time is required")
    private Long expTime;

    public SigninResponseDTO() {
    }

    public SigninResponseDTO(String token, Long expTime) {
        this.token = token;
        this.expTime = expTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }
}
