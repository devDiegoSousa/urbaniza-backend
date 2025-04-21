package com.urbaniza.authapi.dto.response;

public class SigninResponseDTO {
    private String token;
    private Long expTime;

    public SigninResponseDTO(String token, Long expTime) {
        this.token = token;
        this.expTime = expTime;
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public Long getExpTime() {return expTime;}
    public void setExpTime(Long expTime) {this.expTime = expTime;}
}