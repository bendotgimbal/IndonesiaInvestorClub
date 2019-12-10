package com.example.indonesiainvestorclub.models.response;

import com.google.gson.annotations.SerializedName;

public class LogoutRes {
    @SerializedName("X-API-KEY")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
