package com.example.indonesiainvestorclub.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("X-API-KEY")
    @Expose
    private String api_key;
    @SerializedName("username")
    @Expose
    private String username;
}
