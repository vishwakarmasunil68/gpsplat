package com.voxtrail.gpstracking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationPOJO implements Serializable {
    @SerializedName("AspNetUser")
    @Expose
    private Object aspNetUser;
    @SerializedName("tbl_Imei")
    @Expose
    private Object tblImei;
    @SerializedName("col_AlertsID")
    @Expose
    private Integer colAlertsID;
    @SerializedName("col_Alert")
    @Expose
    private String colAlert;
    @SerializedName("col_ImeiID")
    @Expose
    private Integer colImeiID;
    @SerializedName("col_UserID")
    @Expose
    private String colUserID;
    @SerializedName("col_AlertDateTime")
    @Expose
    private String colAlertDateTime;
    @SerializedName("col_Read")
    @Expose
    private Boolean colRead;
    @SerializedName("col_VehicleNumber")
    @Expose
    private String colVehicleNumber;
    @SerializedName("col_Latitude")
    @Expose
    private String colLatitude;
    @SerializedName("col_Longitude")
    @Expose
    private String colLongitude;
    @SerializedName("col_Speed")
    @Expose
    private Integer colSpeed;

    public Object getAspNetUser() {
        return aspNetUser;
    }

    public void setAspNetUser(Object aspNetUser) {
        this.aspNetUser = aspNetUser;
    }

    public Object getTblImei() {
        return tblImei;
    }

    public void setTblImei(Object tblImei) {
        this.tblImei = tblImei;
    }

    public Integer getColAlertsID() {
        return colAlertsID;
    }

    public void setColAlertsID(Integer colAlertsID) {
        this.colAlertsID = colAlertsID;
    }

    public String getColAlert() {
        return colAlert;
    }

    public void setColAlert(String colAlert) {
        this.colAlert = colAlert;
    }

    public Integer getColImeiID() {
        return colImeiID;
    }

    public void setColImeiID(Integer colImeiID) {
        this.colImeiID = colImeiID;
    }

    public String getColUserID() {
        return colUserID;
    }

    public void setColUserID(String colUserID) {
        this.colUserID = colUserID;
    }

    public String getColAlertDateTime() {
        return colAlertDateTime;
    }

    public void setColAlertDateTime(String colAlertDateTime) {
        this.colAlertDateTime = colAlertDateTime;
    }

    public Boolean getColRead() {
        return colRead;
    }

    public void setColRead(Boolean colRead) {
        this.colRead = colRead;
    }

    public String getColVehicleNumber() {
        return colVehicleNumber;
    }

    public void setColVehicleNumber(String colVehicleNumber) {
        this.colVehicleNumber = colVehicleNumber;
    }

    public String getColLatitude() {
        return colLatitude;
    }

    public void setColLatitude(String colLatitude) {
        this.colLatitude = colLatitude;
    }

    public String getColLongitude() {
        return colLongitude;
    }

    public void setColLongitude(String colLongitude) {
        this.colLongitude = colLongitude;
    }

    public Integer getColSpeed() {
        return colSpeed;
    }

    public void setColSpeed(Integer colSpeed) {
        this.colSpeed = colSpeed;
    }
}
