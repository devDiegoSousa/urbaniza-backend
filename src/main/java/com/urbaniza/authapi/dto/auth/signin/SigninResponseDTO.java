package com.urbaniza.authapi.dto.auth.signin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SigninResponseDTO {

    @NotBlank(message = "Token is required")
    private String accessToken;
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    @NotNull(message = "Expiration time is required")
    private Long expTime;

    @NotBlank(message = "Role is required")
    private String role;

    public SigninResponseDTO() {}
    public SigninResponseDTO(String accessToken,String refreshToken, Long expTime, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expTime = expTime;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpTime() {
        return expTime;
    }
    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
