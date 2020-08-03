package com.project.indonesiainvestorclub.services;

import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.response.GlobalResponse;
import com.project.indonesiainvestorclub.models.response.InvestSlotFundsRes;
import com.project.indonesiainvestorclub.models.response.LoginRes;
import com.project.indonesiainvestorclub.models.response.LogoutRes;
import com.project.indonesiainvestorclub.models.response.ProfileUpdateRes;
import com.project.indonesiainvestorclub.models.response.SignUpRes;
import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.models.response.UpdateImageProofOfBankRes;
import com.project.indonesiainvestorclub.models.response.UpdateImageProofOfIDRes;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InvestorClubService {

  @Multipart
  @POST("login")
  Observable<Response<LoginRes>> loginRequest(@Part("username") RequestBody username, @Part("password") RequestBody password);

  @GET("logout")
  Observable<Response<LogoutRes>> logoutRequest();

  @FormUrlEncoded
  @PUT("public/register")
  Observable<Response<SignUpRes>> signUpRequest(@Field("referral") String referral, @Field("first_name") String first_name
          , @Field("last_name") String last_name, @Field("phone_no") String phone_no, @Field("email") String email, @Field("password") String password);

  @FormUrlEncoded
  @PATCH("profile")
  Observable<Response<ProfileUpdateRes>> profileUpdateRequest(@Field("first_name") String first_name, @Field("last_name") String last_name
          , @Field("date_of_birth") String date_of_birth, @Field("gender") String gender, @Field("marital_status") String marital_status
          , @Field("nationality") String nationality, @Field("address") String address, @Field("city") String city, @Field("postal_code") String postal_code
          , @Field("country") String country, @Field("phone_no") String phone_no, @Field("occupation") String occupation);

  @GET("profile")
  Observable<Response<JsonElement>> profileRequest();

  @GET("public/performance/{page}")
  Observable<Response<JsonElement>> performanceRequest(@Path("page") int page);

  @GET("public/about")
  Observable<Response<JsonElement>> aboutRequest();

  @GET("invest/{id}")
  Observable<Response<JsonElement>> investRequest(@Path("id") String id);

  @Multipart
  @POST("invest/{id}")
  Observable<Response<GlobalResponse>> postInvestRequest(@Path("id") String id, @Part("slot") RequestBody slot);

  @GET("portfolio/{page}")
  Observable<Response<JsonElement>> portfolioRequest(@Path("page") int page);

  @GET("funds")
  Observable<Response<JsonElement>> fundsRequest();

  @GET("funds/{page}")
//  Observable<Response<JsonElement>> fundsSecondRequest(@Path("page") int page);
  Observable<Response<JsonElement>> fundsSecondRequest(@Path("page") String page);

  @GET("agreement")
  Observable<Response<JsonElement>> agreementRequest();

  @GET("lounge")
  Observable<Response<JsonElement>> loungeRequest();

//  @GET("network/{page}")
//  Observable<Response<JsonElement>> networkRequest(@Path("page") int page);

  @GET("network")
  Observable<Response<JsonElement>> networkRequest();

  @GET("network/{id}")
  Observable<Response<JsonElement>> networkRequest(@Path("id") int id);

  @GET("transactions/{page}")
  Observable<Response<JsonElement>> transactionsRequest(@Path("page") int page);

  @Multipart
  @POST("avatar")
  Observable<Response<GlobalResponse>> uploadProfileRequest(@Part MultipartBody.Part profile_image);

  @Multipart
  @POST("documents")
  Observable<Response<UpdateImageProofOfIDRes>> uploadProofIDRequest(@Part MultipartBody.Part proof_id_image);

  @Multipart
  @POST("banks")
  Observable<Response<UpdateImageProofOfBankRes>> uploadProofBankRequest(@Part MultipartBody.Part proof_bank_image);
}
