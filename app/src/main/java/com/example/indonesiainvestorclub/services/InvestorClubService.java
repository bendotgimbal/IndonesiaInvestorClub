package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.models.response.LoginRes;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InvestorClubService {

  @Multipart
  @POST("login")
  Observable<Response<LoginRes>> loginRequest(@Part("username") String username, @Part("password") String password);
}
