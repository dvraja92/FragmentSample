package com.anhad.fleetgaurd.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ranosys on 15/2/16.
 */
public class RestError {
    @SerializedName("reason")
    @Expose
    private String reason;
    @Expose
    private String requestURI;

    /**
     *
     * @return
     * The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     * The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @return
     * The requestURI
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     *
     * @param requestURI
     * The requestURI
     */
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}
