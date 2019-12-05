package com.example.indonesiainvestorclub.services;

import androidx.annotation.NonNull;
import com.example.indonesiainvestorclub.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultInterceptor implements Interceptor {
  @Override
  public Response intercept(@NonNull Interceptor.Chain chain) {
    Request originalRequest = chain.request();
    Request.Builder requestBuilder = originalRequest.newBuilder()
        .header("App-Version", String.valueOf(BuildConfig.VERSION_CODE))
        .method(originalRequest.method(), originalRequest.body());

    Request request = requestBuilder.build();
    Response response = null;
    try {
      response = chain.proceed(request);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }
}
