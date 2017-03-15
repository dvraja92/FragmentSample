package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kalpesh Kumawat on 4/2/17.
 * Company Name: DecipherZoneSoftwares
 * URL: www.decipherzone.com
 */
public class WSRFilterRequestModel {


    @SerializedName("query_type")
    public List<QueryTypeModel> query_type;
    @SerializedName("employee_name")
    public List<EmployeeNameModel> employee_name;
    @SerializedName("status")
    public List<StatusModel> status;

    public List<QueryTypeModel> getQuery_type() {
        return query_type;
    }

    public void setQuery_type(List<QueryTypeModel> query_type) {
        this.query_type = query_type;
    }

    public List<EmployeeNameModel> getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(List<EmployeeNameModel> employee_name) {
        this.employee_name = employee_name;
    }

    public List<StatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<StatusModel> status) {
        this.status = status;
    }

    public static class QueryTypeModel {
        @SerializedName("type")
        public String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class EmployeeNameModel {
        @SerializedName("name")
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class StatusModel {
        @SerializedName("sheet_status")
        public String sheet_status;

        public String getSheet_status() {
            return sheet_status;
        }

        public void setSheet_status(String sheet_status) {
            this.sheet_status = sheet_status;
        }
    }
}
