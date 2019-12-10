package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.models.response.LoginRes;
import com.example.indonesiainvestorclub.models.response.LogoutRes;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InvestorClubService {

  @Multipart
  @POST("login")
  Observable<Response<LoginRes>> loginRequest(@Part("username") RequestBody username, @Part("password") RequestBody password);

  @GET("public/performance")
  Observable<Response<PerformanceRes>> performanceRequest();

  @GET("logout")
  Observable<Response<LogoutRes>> logoutRequest();
}
