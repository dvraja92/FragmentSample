package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anhad on 10-01-2017.
 */
public class AddDSRRequestModel {
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("report_date")
    @Expose
    private String reportDate;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("imei_no")
    @Expose
    private String imeiNo;
    @SerializedName("old_vehicle_no")
    @Expose
    private String oldVehicleNo;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("amount_recive")
    @Expose
    private String amountRecive;
    @SerializedName("special_remark")
    @Expose
    private String specialRemark;
    @SerializedName("party_name")
    @Expose
    private String partyName;
    @SerializedName("new_vehicle_no")
    @Expose
    private String newVehicleNo;
    @SerializedName("old_model")
    @Expose
    private String oldModel;
    @SerializedName("new_model")
    @Expose
    private String newModel;
    @SerializedName("old_device_imei_no")
    @Expose
    private String oldDeviceImeiNo;
    @SerializedName("new_device_imei_no")
    @Expose
    private String newDeviceImeiNo;
    @SerializedName("status")
    @Expose
    private String status;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getOldVehicleNo() {
        return oldVehicleNo;
    }

    public void setOldVehicleNo(String oldVehicleNo) {
        this.oldVehicleNo = oldVehicleNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAmountRecive() {
        return amountRecive;
    }

    public void setAmountRecive(String amountRecive) {
        this.amountRecive = amountRecive;
    }

    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getNewVehicleNo() {
        return newVehicleNo;
    }

    public void setNewVehicleNo(String newVehicleNo) {
        this.newVehicleNo = newVehicleNo;
    }

    public String getOldModel() {
        return oldModel;
    }

    public void setOldModel(String oldModel) {
        this.oldModel = oldModel;
    }

    public String getNewModel() {
        return newModel;
    }

    public void setNewModel(String newModel) {
        this.newModel = newModel;
    }

    public String getOldDeviceImeiNo() {
        return oldDeviceImeiNo;
    }

    public void setOldDeviceImeiNo(String oldDeviceImeiNo) {
        this.oldDeviceImeiNo = oldDeviceImeiNo;
    }

    public String getNewDeviceImeiNo() {
        return newDeviceImeiNo;
    }

    public void setNewDeviceImeiNo(String newDeviceImeiNo) {
        this.newDeviceImeiNo = newDeviceImeiNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
