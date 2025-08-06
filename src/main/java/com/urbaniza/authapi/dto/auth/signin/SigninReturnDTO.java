package com.urbaniza.authapi.dto.auth.signin;

public class SigninReturnDTO {
  private String accessToken;
  private long expTime;
  private String refreshToken;

  public SigninReturnDTO(String accessToken, long expTime, String refreshToken) {
    this.accessToken = accessToken;
    this.expTime = expTime;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {return accessToken;}
  public void setAccessToken(String accessToken) {this.accessToken = accessToken;}

  public String getRefreshToken() {return refreshToken;}
  public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}

  public long getExpTime() {return expTime;}
  public void setExpTime(long expTime) {this.expTime = expTime;}
}
