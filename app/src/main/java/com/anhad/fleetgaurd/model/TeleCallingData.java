package com.anhad.fleetgaurd.model;

import com.anhad.fleetgaurd.model.TeleCallInnerModel.*;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL  374 on 12/30/2016.
 */

public class TeleCallingData {

    @SerializedName("TeleCall")
    private TeleCall teleCall;
    @SerializedName("Location")
    private Location location;
    @SerializedName("Admin")
    private Admin admin;
    @SerializedName("DeviceSuggestion")
    private List<DeviceSuggestion> deviceSuggestionList;

    public TeleCall getTeleCall() {
        return teleCall;
    }

    public Location getLocation() {
        return location;
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<DeviceSuggestion> getDeviceSuggestionList() {
        return deviceSuggestionList;
    }
}