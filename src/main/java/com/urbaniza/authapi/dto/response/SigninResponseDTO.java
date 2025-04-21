package com.urbaniza.authapi.dto.response;

public class SigninResponseDTO {
    private String token;

    public SigninResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
}