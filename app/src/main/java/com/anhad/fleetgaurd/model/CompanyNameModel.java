package com.anhad.fleetgaurd.model;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by DELL  374 on 12/30/2016.
 */

public class CompanyNameModel
{
    private boolean status;
    private String Message;
    @SerializedName("CompanyData")
    private List<CompanyData> companyDataList;

    public List<CompanyData> getCompanyDataList() {
        return companyDataList;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return Message;
    }

    public static CompanyNameModel parseResponse(String response) {
        CompanyNameModel model = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(response.toString().getBytes());
            Reader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            try {
                Type listType = new TypeToken<CompanyNameModel>() {
                }.getType();
                model = gson.fromJson(reader, listType);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (JsonIOException e) {
                e.printStackTrace();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
