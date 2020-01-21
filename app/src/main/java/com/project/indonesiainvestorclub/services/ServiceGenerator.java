package com.project.indonesiainvestorclub.services;

import com.project.indonesiainvestorclub.BuildConfig;
import com.project.indonesiainvestorclub.IndonesiaInvestorClub;
import com.readystatesoftware.chuck.ChuckInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  private static final int GLOBAL_TIMEOUT = 100;

  public static Retrofit retrofit;
  public static InvestorClubService service;

  private static Retrofit.Builder retrofitBuilder =
      new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create());
  private static HttpLoggingInterceptor loggingInterceptor =
      new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

  static {
    OkHttpClient.Builder httpClientBuilder =
        new OkHttpClient.Builder().connectTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(GLOBAL_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(new DefaultInterceptor());

      httpClientBuilder.addInterceptor(new ChuckInterceptor(IndonesiaInvestorClub.getApplication()));

    if (!httpClientBuilder.interceptors().contains(loggingInterceptor) && BuildConfig.DEBUG) {
      httpClientBuilder.addInterceptor(loggingInterceptor);
    }

    retrofitBuilder.client(httpClientBuilder.build());
    retrofit = retrofitBuilder.build();
    service = retrofit.create(InvestorClubService.class);
  }

  public ServiceGenerator() {

  }
}
