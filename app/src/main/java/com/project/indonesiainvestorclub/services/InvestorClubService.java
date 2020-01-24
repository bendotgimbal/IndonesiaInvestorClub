package com.project.indonesiainvestorclub.services;

import com.project.indonesiainvestorclub.models.response.LoginRes;
import com.project.indonesiainvestorclub.models.response.LogoutRes;
import com.project.indonesiainvestorclub.models.response.ProfileUpdateRes;
import com.project.indonesiainvestorclub.models.response.SignUpRes;
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
  Observable<Response<SignUpRes>> signUpRequest(@Part("referral") RequestBody referral, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("phone_no") RequestBody phone_no, @Part("email") RequestBody email, @Part("password") RequestBody password);

  @GET("profile")
  Observable<Response<JsonElement>> profileRequest();

  @GET("public/performance")
  Observable<Response<JsonElement>> performanceRequest();

  @GET("public/about")
  Observable<Response<JsonElement>> aboutRequest();

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
