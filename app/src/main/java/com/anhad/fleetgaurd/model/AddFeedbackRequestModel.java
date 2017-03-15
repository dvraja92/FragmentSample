package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anhad on 10-01-2017.
 */
public class AddFeedbackRequestModel {
    @SerializedName("customer_name")
    @Expose
    private String customerName;

    @SerializedName("contact_number")
    @Expose
    private String customerNo;

    @SerializedName("location_id")
    @Expose
    private String locationId;

    @SerializedName("call_back")
    @Expose
    private String callBack;

    @SerializedName("review")
    @Expose
    private String review;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}


