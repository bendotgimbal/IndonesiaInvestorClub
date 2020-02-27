package com.project.indonesiainvestorclub.models.response;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.project.indonesiainvestorclub.models.Groups;

import java.util.List;

public class LoginRes {
  @SerializedName("X-API-KEY")
  private String token;
  private String username;
  @SerializedName("Avatar")
  private String avatar;
  @SerializedName("Groups")
  private JsonElement groups;
  @SerializedName("FirstName")
  private String firstName;
  @SerializedName("LastName")
  private String lastName;

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

  public JsonElement getGroups() {
    return groups;
  }

  public void setGroups(JsonElement groups) {
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
