package com.urbaniza.authapi.dto.auth.refresh;


public class RefreshTokenResponseDTO {
    private String accessToken;
    private long expTime;

    public RefreshTokenResponseDTO() {}

    public RefreshTokenResponseDTO(String accessToken, String refreshToken, long expTime) {
        this.accessToken = accessToken;
        this.expTime = expTime;
    }

    public String getAccessToken() {return accessToken;}
    public void setAccessToken(String accessToken) {this.accessToken = accessToken;}

    public long getExpTime() {return expTime;}
    public void setExpTime(long expTime) {this.expTime = expTime;}

}