package com.anhad.fleetgaurd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anhad on 10-01-2017.
 */
public class AdminWorkSheetReportModel implements Serializable{
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("WorkSheetData")
    @Expose
    private ArrayList<WorkSheetDatum> workSheetData = null;

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

    public ArrayList<WorkSheetDatum> getWorkSheetData() {
        return workSheetData;
    }

    public void setWorkSheetData(ArrayList<WorkSheetDatum> workSheetData) {
        this.workSheetData = workSheetData;
    }


    public class Location {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class CompanyName {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    }

    public class WorkHandover {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("handover_id")
        @Expose
        private String handoverId;
        @SerializedName("work_sheet_id")
        @Expose
        private String workSheetId;
        @SerializedName("created")
        @Expose
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

    }

    public class WorkSheet {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("report_type")
        @Expose
        private String reportType;
        @SerializedName("complain_id")
        @Expose
        private String complainId;
        @SerializedName("complain_number")
        @Expose
        private String complainNumber;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("location_id")
        @Expose
        private String locationId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("allotment_user_name")
        @Expose
        private Object allotmentUserName;
        @SerializedName("contact_person")
        @Expose
        private String contactPerson;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("company_category")
        @Expose
        private Object companyCategory;
        @SerializedName("query_type")
        @Expose
        private String queryType;
        @SerializedName("remarks")
        @Expose
        private String remarks;
        @SerializedName("old_vehicle_number")
        @Expose
        private Object oldVehicleNumber;
        @SerializedName("new_vehicle_number")
        @Expose
        private Object newVehicleNumber;
        @SerializedName("modal_number")
        @Expose
        private Object modalNumber;
        @SerializedName("imei_number")
        @Expose
        private Object imeiNumber;
        @SerializedName("vehicle_number")
        @Expose
        private Object vehicleNumber;
        @SerializedName("problem_type")
        @Expose
        private Object problemType;
        @SerializedName("device_type")
        @Expose
        private Object deviceType;
        @SerializedName("warranty")
        @Expose
        private Object warranty;
        @SerializedName("amount_charge")
        @Expose
        private Object amountCharge;
        @SerializedName("amount_collected")
        @Expose
        private Object amountCollected;
        @SerializedName("service_use")
        @Expose
        private Object serviceUse;
        @SerializedName("old_device_modal")
        @Expose
        private Object oldDeviceModal;
        @SerializedName("new_device_modal")
        @Expose
        private Object newDeviceModal;
        @SerializedName("old_imei_number")
        @Expose
        private Object oldImeiNumber;
        @SerializedName("new_imei_number")
        @Expose
        private Object newImeiNumber;
        @SerializedName("meeting_time")
        @Expose
        private Object meetingTime;
        @SerializedName("meeting_date")
        @Expose
        private Object meetingDate;
        @SerializedName("meeting_type")
        @Expose
        private Object meetingType;
        @SerializedName("previous_meeting_date")
        @Expose
        private Object previousMeetingDate;
        @SerializedName("meeting_reason")
        @Expose
        private Object meetingReason;
        @SerializedName("meeting_outcome")
        @Expose
        private Object meetingOutcome;
        @SerializedName("device_suggestion")
        @Expose
        private Object deviceSuggestion;
        @SerializedName("price_given")
        @Expose
        private Object priceGiven;
        @SerializedName("next_visit")
        @Expose
        private Object nextVisit;
        @SerializedName("next_meeting_date")
        @Expose
        private Object nextMeetingDate;
        @SerializedName("reference_name")
        @Expose
        private Object referenceName;
        @SerializedName("device_quantity")
        @Expose
        private Object deviceQuantity;
        @SerializedName("negative_close_reason")
        @Expose
        private Object negativeCloseReason;
        @SerializedName("old_sim_number")
        @Expose
        private Object oldSimNumber;
        @SerializedName("new_sim_number")
        @Expose
        private Object newSimNumber;
        @SerializedName("device_imei_number")
        @Expose
        private Object deviceImeiNumber;
        @SerializedName("date")
        @Expose
        private Object date;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("employe_status")
        @Expose
        private String employeStatus;
        @SerializedName("my_vehicle_number")
        @Expose
        private String myVehicleNumber;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("modified")
        @Expose
        private String modified;

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

        public String getComplainId() {
            return complainId;
        }

        public void setComplainId(String complainId) {
            this.complainId = complainId;
        }

        public String getComplainNumber() {
            return complainNumber;
        }

        public void setComplainNumber(String complainNumber) {
            this.complainNumber = complainNumber;
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

        public Object getAllotmentUserName() {
            return allotmentUserName;
        }

        public void setAllotmentUserName(Object allotmentUserName) {
            this.allotmentUserName = allotmentUserName;
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

        public Object getCompanyCategory() {
            return companyCategory;
        }

        public void setCompanyCategory(Object companyCategory) {
            this.companyCategory = companyCategory;
        }

        public String getQueryType() {
            return queryType;
        }

        public void setQueryType(String queryType) {
            this.queryType = queryType;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Object getOldVehicleNumber() {
            return oldVehicleNumber;
        }

        public void setOldVehicleNumber(Object oldVehicleNumber) {
            this.oldVehicleNumber = oldVehicleNumber;
        }

        public Object getNewVehicleNumber() {
            return newVehicleNumber;
        }

        public void setNewVehicleNumber(Object newVehicleNumber) {
            this.newVehicleNumber = newVehicleNumber;
        }

        public Object getModalNumber() {
            return modalNumber;
        }

        public void setModalNumber(Object modalNumber) {
            this.modalNumber = modalNumber;
        }

        public Object getImeiNumber() {
            return imeiNumber;
        }

        public void setImeiNumber(Object imeiNumber) {
            this.imeiNumber = imeiNumber;
        }

        public Object getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(Object vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public Object getProblemType() {
            return problemType;
        }

        public void setProblemType(Object problemType) {
            this.problemType = problemType;
        }

        public Object getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Object deviceType) {
            this.deviceType = deviceType;
        }

        public Object getWarranty() {
            return warranty;
        }

        public void setWarranty(Object warranty) {
            this.warranty = warranty;
        }

        public Object getAmountCharge() {
            return amountCharge;
        }

        public void setAmountCharge(Object amountCharge) {
            this.amountCharge = amountCharge;
        }

        public Object getAmountCollected() {
            return amountCollected;
        }

        public void setAmountCollected(Object amountCollected) {
            this.amountCollected = amountCollected;
        }

        public Object getServiceUse() {
            return serviceUse;
        }

        public void setServiceUse(Object serviceUse) {
            this.serviceUse = serviceUse;
        }

        public Object getOldDeviceModal() {
            return oldDeviceModal;
        }

        public void setOldDeviceModal(Object oldDeviceModal) {
            this.oldDeviceModal = oldDeviceModal;
        }

        public Object getNewDeviceModal() {
            return newDeviceModal;
        }

        public void setNewDeviceModal(Object newDeviceModal) {
            this.newDeviceModal = newDeviceModal;
        }

        public Object getOldImeiNumber() {
            return oldImeiNumber;
        }

        public void setOldImeiNumber(Object oldImeiNumber) {
            this.oldImeiNumber = oldImeiNumber;
        }

        public Object getNewImeiNumber() {
            return newImeiNumber;
        }

        public void setNewImeiNumber(Object newImeiNumber) {
            this.newImeiNumber = newImeiNumber;
        }

        public Object getMeetingTime() {
            return meetingTime;
        }

        public void setMeetingTime(Object meetingTime) {
            this.meetingTime = meetingTime;
        }

        public Object getMeetingDate() {
            return meetingDate;
        }

        public void setMeetingDate(Object meetingDate) {
            this.meetingDate = meetingDate;
        }

        public Object getMeetingType() {
            return meetingType;
        }

        public void setMeetingType(Object meetingType) {
            this.meetingType = meetingType;
        }

        public Object getPreviousMeetingDate() {
            return previousMeetingDate;
        }

        public void setPreviousMeetingDate(Object previousMeetingDate) {
            this.previousMeetingDate = previousMeetingDate;
        }

        public Object getMeetingReason() {
            return meetingReason;
        }

        public void setMeetingReason(Object meetingReason) {
            this.meetingReason = meetingReason;
        }

        public Object getMeetingOutcome() {
            return meetingOutcome;
        }

        public void setMeetingOutcome(Object meetingOutcome) {
            this.meetingOutcome = meetingOutcome;
        }

        public Object getDeviceSuggestion() {
            return deviceSuggestion;
        }

        public void setDeviceSuggestion(Object deviceSuggestion) {
            this.deviceSuggestion = deviceSuggestion;
        }

        public Object getPriceGiven() {
            return priceGiven;
        }

        public void setPriceGiven(Object priceGiven) {
            this.priceGiven = priceGiven;
        }

        public Object getNextVisit() {
            return nextVisit;
        }

        public void setNextVisit(Object nextVisit) {
            this.nextVisit = nextVisit;
        }

        public Object getNextMeetingDate() {
            return nextMeetingDate;
        }

        public void setNextMeetingDate(Object nextMeetingDate) {
            this.nextMeetingDate = nextMeetingDate;
        }

        public Object getReferenceName() {
            return referenceName;
        }

        public void setReferenceName(Object referenceName) {
            this.referenceName = referenceName;
        }

        public Object getDeviceQuantity() {
            return deviceQuantity;
        }

        public void setDeviceQuantity(Object deviceQuantity) {
            this.deviceQuantity = deviceQuantity;
        }

        public Object getNegativeCloseReason() {
            return negativeCloseReason;
        }

        public void setNegativeCloseReason(Object negativeCloseReason) {
            this.negativeCloseReason = negativeCloseReason;
        }

        public Object getOldSimNumber() {
            return oldSimNumber;
        }

        public void setOldSimNumber(Object oldSimNumber) {
            this.oldSimNumber = oldSimNumber;
        }

        public Object getNewSimNumber() {
            return newSimNumber;
        }

        public void setNewSimNumber(Object newSimNumber) {
            this.newSimNumber = newSimNumber;
        }

        public Object getDeviceImeiNumber() {
            return deviceImeiNumber;
        }

        public void setDeviceImeiNumber(Object deviceImeiNumber) {
            this.deviceImeiNumber = deviceImeiNumber;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
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

        public String getMyVehicleNumber() {
            return myVehicleNumber;
        }

        public void setMyVehicleNumber(String myVehicleNumber) {
            this.myVehicleNumber = myVehicleNumber;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

    }

    public class WorkSheetDatum {

        @SerializedName("WorkSheet")
        @Expose
        private WorkSheet workSheet;
        @SerializedName("CompanyName")
        @Expose
        private CompanyName companyName;
        @SerializedName("Location")
        @Expose
        private Location location;
        @SerializedName("Admin")
        @Expose
        private Admin admin;
        @SerializedName("WorkHandover")
        @Expose
        private WorkHandover workHandover;

        public WorkSheet getWorkSheet() {
            return workSheet;
        }

        public void setWorkSheet(WorkSheet workSheet) {
            this.workSheet = workSheet;
        }

        public CompanyName getCompanyName() {
            return companyName;
        }

        public void setCompanyName(CompanyName companyName) {
            this.companyName = companyName;
        }

        public Location getLocation() {
            return location;
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

        public WorkHandover getWorkHandover() {
            return workHandover;
        }

        public void setWorkHandover(WorkHandover workHandover) {
            this.workHandover = workHandover;
        }

    }
}
