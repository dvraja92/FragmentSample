package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikita Choudhary on 1/2/17.
 * Company Name: DecipherZoneSoftwares
 * URL: www.decipherzone.com
 */
public class ForgotPasswordRequestModel {

    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
