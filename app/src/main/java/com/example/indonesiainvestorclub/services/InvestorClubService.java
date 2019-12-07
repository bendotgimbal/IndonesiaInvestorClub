package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.models.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InvestorClubService {

    @FormUrlEncoded
    @POST("login/{username}/{password}")
    Call<Login> loginRequest(@Field("username") String username, @Field("password") String password);
}
