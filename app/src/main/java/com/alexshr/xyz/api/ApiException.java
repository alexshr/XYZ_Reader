package com.alexshr.xyz.api;

import java.io.IOException;

import lombok.Getter;
import lombok.ToString;
import retrofit2.Response;
import timber.log.Timber;

@ToString
public class ApiException extends Exception {
    private int mCode;
    @Getter
    private String message;

    public static ApiException parseError(Response<?> response) {
        try {
            ApiException apiExeption = new ApiException();
            apiExeption.mCode = response.code();
            apiExeption.message = "http error: response code=" + apiExeption.mCode;
            if (response.errorBody() != null)
                apiExeption.message += "; " + response.errorBody().string();

            Timber.e(apiExeption);
            return apiExeption;
        } catch (IOException e) {
            Timber.e(e);
            return new ApiException();
        }
    }
}
