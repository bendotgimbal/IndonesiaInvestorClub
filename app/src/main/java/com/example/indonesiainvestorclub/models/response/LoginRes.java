package com.example.indonesiainvestorclub.models.response;

import com.google.gson.annotations.SerializedName;

public class LoginRes {
  @SerializedName("X-API-KEY")
  private String token;
  private String username;
  private String avatar;
  private String firstname;
  private String lastname;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getFirstName() {
    return firstname;
  }

  public void setFirstName(String firstname) {
    this.firstname = firstname;
  }

  public String getLastName() {
    return lastname;
  }

  public void setLastName(String lastname) {
    this.lastname = lastname;
  }
}
