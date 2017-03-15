package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anhad on 11-01-2017.
 */
public class StatusUpdateRequestModel {
    @SerializedName("reportId")
    @Expose
    private String reportId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
