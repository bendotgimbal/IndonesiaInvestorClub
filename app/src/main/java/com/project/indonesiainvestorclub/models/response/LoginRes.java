package com.project.indonesiainvestorclub.models.response;

import com.google.gson.annotations.SerializedName;
import com.project.indonesiainvestorclub.models.Groups;

import java.util.List;

public class LoginRes {
  @SerializedName("X-API-KEY")
  private String token;
  private String username;
  @SerializedName("Avatar")
  private String avatar;
  private List<Groups> groups;
  @SerializedName("FirstName")
  private String firstName;
  @SerializedName("LastName")
  private String lastName;

  public LoginRes(String token, String username, String avatar, List<Groups> groups, String firstName,
                  String lastName) {
    this.token = token;
    this.username = username;
    this.avatar = avatar;
    this.groups = groups;
    this.firstName = firstName;
    this.lastName = lastName;
  }

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

  public List<Groups> getGroups() {
    return groups;
  }

  public void setGroups(List<Groups> groups) {
    this.groups = groups;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
