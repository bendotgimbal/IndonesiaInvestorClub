package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.models.response.FundsRes;
import com.example.indonesiainvestorclub.models.response.InvestRes;
import com.example.indonesiainvestorclub.models.response.LoginRes;
import com.example.indonesiainvestorclub.models.response.LogoutRes;
import com.example.indonesiainvestorclub.models.response.NetworkRes;
import com.example.indonesiainvestorclub.models.response.PortfolioRes;
import com.example.indonesiainvestorclub.models.response.ProfileRes;
import com.example.indonesiainvestorclub.models.response.ProfileUpdateRes;
import com.example.indonesiainvestorclub.models.response.SignUpRes;
import com.example.indonesiainvestorclub.models.response.TransactionsRes;
import com.google.gson.JsonElement;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface InvestorClubService {

  @Multipart
  @POST("login")
  Observable<Response<LoginRes>> loginRequest(@Part("username") RequestBody username, @Part("password") RequestBody password);

  @GET("logout")
  Observable<Response<LogoutRes>> logoutRequest();

  @PUT("register")
  Observable<Response<SignUpRes>> signUpRequest();

  @GET("profile")
  Observable<Response<JsonElement>> profileRequest();

  @GET("public/performance")
  Observable<Response<JsonElement>> performanceRequest();

  @GET("invest/{id}")
  Observable<Response<JsonElement>> investRequest(@Path("id") String id);

  @GET("portfolio/{page}")
  Observable<Response<JsonElement>> portfolioRequest(@Path("page") int page);

  @GET("funds")
  Observable<Response<JsonElement>> fundsRequest();

  @GET("agreement")
  Observable<Response<JsonElement>> agreementRequest();

  @GET("lounge")
  Observable<Response<JsonElement>> loungeRequest();

  @GET("network/{page}")
  Observable<Response<JsonElement>> networkRequest(@Path("page") int page);

  @GET("transactions/{page}")
  Observable<Response<JsonElement>> transactionsRequest(@Path("page") int page);

  @PATCH("profile")
  Observable<Response<ProfileUpdateRes>> profileupdateRequest();
}
