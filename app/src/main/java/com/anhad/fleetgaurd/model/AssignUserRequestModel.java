package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anhad on 11-01-2017.
 */
public class AssignUserRequestModel {
    @SerializedName("handover_id")
    @Expose
    private String handoverId;
    @SerializedName("work_sheet_id")
    @Expose
    private String workSheetId;

    public String getHandoverId() {
        return handoverId;
    }

    public void setHandoverId(String handoverId) {
        this.handoverId = handoverId;
    }

    public String getWorkSheetId() {
        return workSheetId;
    }

    public void setWorkSheetId(String workSheetId) {
        this.workSheetId = workSheetId;
    }
}
