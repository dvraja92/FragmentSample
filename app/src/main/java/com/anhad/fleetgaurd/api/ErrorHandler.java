package com.anhad.fleetgaurd.api;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ranosys on 15/2/16.
 */
public class ErrorHandler implements retrofit.ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause != null) {
            try {
                Response response = cause.getResponse();
                RestError restError = (RestError) cause.getBodyAs(RestError.class);
                if (response != null)
                    return new Exception(restError != null ? restError.getReason() : "Unknown error, please try again.");
            } catch (Exception ex) {
                return new Exception("Unknown error, please try again.");
            }
        }
        return new Exception("Server Timeout error");
    }
}
