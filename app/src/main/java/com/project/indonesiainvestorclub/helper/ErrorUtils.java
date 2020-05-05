package com.project.indonesiainvestorclub.helper;

import androidx.annotation.Nullable;

import com.project.indonesiainvestorclub.models.ApiError;
import com.project.indonesiainvestorclub.services.ServiceGenerator;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
        @Nullable
        public static ApiError parseError(Response<?> response) {
            Converter<ResponseBody, ApiError> converter =
                    ServiceGenerator.retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);

            ResponseBody responseBody = response.errorBody();

            if (responseBody == null) {
                return null;
            }

            ApiError apiError;

            try {
                apiError = converter.convert(responseBody);
            } catch (IOException e) {
                return null;
            }

            return apiError;
        }

}
