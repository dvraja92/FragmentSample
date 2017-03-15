package com.anhad.fleetgaurd.api;

import android.app.ProgressDialog;
import android.content.Context;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.model.AddDSRResponseModel;
import com.anhad.fleetgaurd.utility.Utility;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class ApiClient implements Callback {

    //Live
  public final static String API_URL = "http://www.fleetguardcare.in/webservices";


    //Local

 //   public final static String API_URL = "http://192.168.0.109/lara-app/webservices";


    //public final static String API_URL = "http://www.fleetguardcare.in/webservices";
    private static ApiInterface.OnComplete onCompleteCallback = null;
    public static ProgressDialog dialog;
    private static ApiClient apiClient = null;
    private final static String DEVICE_TYPE = "ANDROID";
    public static final long REQUEST_TIMEOUT = 30;

    // private constructor to stop initializing this class
    private ApiClient() {

    }


    private static RestAdapter getRestAdapter() {
        apiClient = new ApiClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ErrorHandler())
                .setClient(new OkClient(setOkHttpTimeout(REQUEST_TIMEOUT)))
                .build();

        return restAdapter;
    }

    /*private static RestAdapter getRestAdapter(final String token) {
        apiClient = new ApiClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ErrorHandler())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", "bearer " + token);
                    }
                })
                .setClient(new OkClient(setOkHttpTimeout(REQUEST_TIMEOUT)))
                .build();

        return restAdapter;
    }*/

    @Override
    public void success(Object o, Response response) {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();

        if (onCompleteCallback != null && o != null)
            onCompleteCallback.onSuccess(o);
    }

    @Override
    public void failure(RetrofitError error) {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();

        if (onCompleteCallback != null)
            onCompleteCallback.onError(error);
    }

    public static class ApiManagement {

        private static ApiInterface.ApiManagement apiManagement = null;

        private static ApiInterface.ApiManagement getApiManagement() {
            apiManagement = getRestAdapter().create(ApiInterface.ApiManagement.class);
            return apiManagement;
        }

        public static void addWorkSheet(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().addWorkSheet(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void addDSR(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().addDSR(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void addExpense(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().addExpense(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void assignUser(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().assignUser(model, new Callback<AddDSRResponseModel>() {
                    @Override
                    public void success(AddDSRResponseModel addDSRResponseModel, Response response) {
                        callback.onSuccess(addDSRResponseModel);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        callback.onError(error);
                    }
                });
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void adminStatus(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().adminStatusUpdate(model, new Callback<AddDSRResponseModel>() {
                    @Override
                    public void success(AddDSRResponseModel addDSRResponseModel, Response response) {
                        callback.onSuccess(addDSRResponseModel);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        callback.onError(error);
                    }
                });
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void employeeStatusUpdate(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().employeeStatusUpdate(model, new Callback<AddDSRResponseModel>() {
                    @Override
                    public void success(AddDSRResponseModel addDSRResponseModel, Response response) {
                        callback.onSuccess(addDSRResponseModel);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        callback.onError(error);
                    }
                });
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void addFeedback(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().addFeedBack(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void doLogin(Context context,
                                        JSONObject model, boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().login(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }


        public static void doForgotPassword(Context context,
                                   JSONObject model, boolean progress,
                                   final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().forgotPasswordOnMail(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void getAllLocations(Context context,
                                        boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress,callback);
                ApiClient.ApiManagement.getApiManagement().getAllLocation(apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void getAllUserListing(Context context,
                                        boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress,callback);
                ApiClient.ApiManagement.getApiManagement().getAllUserListing(apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void getWSR(Context context,
                                        boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress,callback);
                ApiClient.ApiManagement.getApiManagement().getWSR(apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }
        public static void doFiltration(Context context,
                                            JSONObject model, boolean progress,
                                            final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress, callback);
                ApiClient.ApiManagement.getApiManagement().onFilterData(model,apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }

        public static void getAssignedWSR(Context context,
                                        boolean progress,
                                        final ApiInterface.OnComplete callback) {
            if (Utility.isNetworkConnected(context)) {
                setDialogAndCallback(context,progress,callback);
                ApiClient.ApiManagement.getApiManagement().getAssignedWSR(apiClient);
            } else
                Utility.showDialog(context, context.getString(R.string.no_internet_connection));
        }
    }

    private static void setDialogAndCallback(Context context, ApiInterface.OnComplete callback) {
        dialog = Util.getProgressDialog(context, context.getResources().getString(R.string.please_wait));
        onCompleteCallback = callback;
    }

    private static void setDialogAndCallback(Context context, boolean progress, ApiInterface.OnComplete callback) {
        if (progress) {
            dialog = Util.getProgressDialog(context, context.getResources().getString(R.string.please_wait));
        }
        onCompleteCallback = callback;
    }

    private static void setCallback(ApiInterface.OnComplete callback) {
        onCompleteCallback = callback;
    }
    private static OkHttpClient setOkHttpTimeout(long timeout) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(timeout, TimeUnit.SECONDS);
        return okHttpClient;
    }
}
