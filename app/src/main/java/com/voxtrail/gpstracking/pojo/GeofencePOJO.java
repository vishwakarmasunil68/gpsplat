package com.voxtrail.gpstracking.pojo;

import java.io.Serializable;

public class GeofencePOJO implements Serializable{
    String fenceID;
    double X;
    double Y;
    String imei;
    String UserID;
    String FenceAddress;
    String VehicleNumber;
    String InTime;
    String OutTime;
    String Duration;

    public String getFenceID() {
        return fenceID;
    }

    public void setFenceID(String fenceID) {
        this.fenceID = fenceID;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFenceAddress() {
        return FenceAddress;
    }

    public void setFenceAddress(String fenceAddress) {
        FenceAddress = fenceAddress;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    @Override
    public String toString() {
        return "GeofencePOJO{" +
                "fenceID='" + fenceID + '\'' +
                ", X=" + X +
                ", Y=" + Y +
                ", imei='" + imei + '\'' +
                ", UserID='" + UserID + '\'' +
                ", FenceAddress='" + FenceAddress + '\'' +
                ", VehicleNumber='" + VehicleNumber + '\'' +
                ", InTime='" + InTime + '\'' +
                ", OutTime='" + OutTime + '\'' +
                ", Duration='" + Duration + '\'' +
                '}';
    }
}
