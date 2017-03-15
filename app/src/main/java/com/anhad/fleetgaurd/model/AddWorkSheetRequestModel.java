package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anhad on 06-01-2017.
 */
public class AddWorkSheetRequestModel {
    @SerializedName("report_type")
    @Expose
    private String reportType;
    @SerializedName("compamy_type")
    @Expose
    private String compamyType;
    @SerializedName("company_name_text")
    @Expose
    private String companyNameText;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("query_type")
    @Expose
    private String queryType;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("my_vehicle_number")
    @Expose
    private String myVehicleNumber;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCompamyType() {
        return compamyType;
    }

    public void setCompamyType(String compamyType) {
        this.compamyType = compamyType;
    }

    public String getCompanyNameText() {
        return companyNameText;
    }

    public void setCompanyNameText(String companyNameText) {
        this.companyNameText = companyNameText;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getMyVehicleNumber() {
        return myVehicleNumber;
    }

    public void setMyVehicleNumber(String myVehicleNumber) {
        this.myVehicleNumber = myVehicleNumber;
    }

}
