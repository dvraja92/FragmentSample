package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Anhad on 11-01-2017.
 */
public class AssignedWorksheetResponseModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("assignWorkData")
    @Expose
    private ArrayList<AssignWorkDatum> assignWorkData = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AssignWorkDatum> getAssignWorkData() {
        return assignWorkData;
    }

    public void setAssignWorkData(ArrayList<AssignWorkDatum> assignWorkData) {
        this.assignWorkData = assignWorkData;
    }
    public class Location {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class CompanyName {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class AssignWorkDatum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("report_type")
        @Expose
        private String reportType;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("location_id")
        @Expose
        private String locationId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("next_visit")
        @Expose
        private Object nextVisit;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("employe_status")
        @Expose
        private String employeStatus;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("CompanyName")
        @Expose
        private CompanyName companyName;
        @SerializedName("Location")
        @Expose
        private Object location;
        @SerializedName("Admin")
        @Expose
        private Admin admin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getNextVisit() {
            return nextVisit;
        }

        public void setNextVisit(Object nextVisit) {
            this.nextVisit = nextVisit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEmployeStatus() {
            return employeStatus;
        }

        public void setEmployeStatus(String employeStatus) {
            this.employeStatus = employeStatus;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public CompanyName getCompanyName() {
            return companyName;
        }

        public void setCompanyName(CompanyName companyName) {
            this.companyName = companyName;
        }

        public Object getLocation() {
            return (Object)location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

    }

    public class Admin {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("full_name")
        @Expose
        private String fullName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }
}
