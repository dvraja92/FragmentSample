package com.anhad.fleetgaurd.api;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.model.AddWorkSheetResponseModel;
import com.anhad.fleetgaurd.model.AdminWorkSheetReportModel;
import com.anhad.fleetgaurd.model.AssignedWorksheetResponseModel;
import com.anhad.fleetgaurd.model.ForgotPasswordResponseModel;
import com.anhad.fleetgaurd.model.GetAllLocationResponseModel;
import com.anhad.fleetgaurd.model.LoginResponseModel;
import com.anhad.fleetgaurd.model.UserListingModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {
    interface OnComplete {
        void onSuccess(Object object);
        void onError(RetrofitError error);
    }

    interface ApiManagement {
        @FormUrlEncoded
        @POST("/addWorkSheet")
        void addWorkSheet(@Field("json") JSONObject model, Callback<AddWorkSheetResponseModel> callback);

        @FormUrlEncoded
        @POST("/addDSR")
        void addDSR(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/addFeedback")
        void addFeedBack(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/addExpenses")
        void addExpense(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/adminStatusUpdate")
        void adminStatusUpdate(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/employeeStatusUpdate")
        void employeeStatusUpdate(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/assignUser")
        void assignUser(@Field("json") JSONObject model, Callback<AddDSRResponseModel> callback);

        @FormUrlEncoded
        @POST("/login")
        void login(@Field("json") JSONObject model, Callback<LoginResponseModel> callback);

        @FormUrlEncoded
        @POST("/forgotPasswordOnMail")
        void forgotPasswordOnMail(@Field("json") JSONObject model, Callback<ForgotPasswordResponseModel> callback);


        @FormUrlEncoded
        @POST("/gethistoryFilter")
        void onFilterData(@Field("json") JSONObject model, Callback<AdminWorkSheetReportModel> callback);

        @GET("/getAllLocation")
        void getAllLocation(Callback<GetAllLocationResponseModel> callback);

        @GET("/getAssignUserListing")
        void getAllUserListing(Callback<UserListingModel> callback);

        @GET("/adminWorksheets")
        void getWSR(Callback<AdminWorkSheetReportModel> callback);

        @GET("/assignedWorkSheets")
        void getAssignedWSR(Callback<AssignedWorksheetResponseModel> callback);
    }
}
