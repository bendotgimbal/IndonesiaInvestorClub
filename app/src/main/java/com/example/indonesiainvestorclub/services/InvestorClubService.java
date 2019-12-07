package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.models.response.Login;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InvestorClubService {

    @FormUrlEncoded
    @POST("/login")
    Observable<Response<Login>> loginRequest(@Field("username") String username, @Field("password") String password);
}
