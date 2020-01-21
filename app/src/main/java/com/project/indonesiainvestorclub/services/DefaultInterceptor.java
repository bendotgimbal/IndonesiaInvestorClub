package com.project.indonesiainvestorclub.services;

import androidx.annotation.NonNull;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import java.util.Objects;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultInterceptor implements Interceptor {
  @Override
  public Response intercept(@NonNull Interceptor.Chain chain) {
    Request originalRequest = chain.request();
    Request.Builder requestBuilder = originalRequest.newBuilder()
        .header("X-API-KEY", Objects.requireNonNull(SharedPreferenceHelper.getToken()))
        .header("Cookie", Objects.requireNonNull(SharedPreferenceHelper.getCookie()))
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
