package com.urbaniza.authapi.dto.response;

public class AuthResponseDTO {
    private String token;
    private Long userId;
    private String email;

    public AuthResponseDTO(String token, Long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
