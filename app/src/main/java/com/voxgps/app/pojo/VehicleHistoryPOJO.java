package com.voxgps.app.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleHistoryPOJO {
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Lng")
    @Expose
    private Double lng;
    @SerializedName("Altitude")
    @Expose
    private String altitude;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("lastTime")
    @Expose
    private String lastTime;
    @SerializedName("timeSpan")
    @Expose
    private String timeSpan;
    @SerializedName("Speed")
    @Expose
    private Integer speed;
    @SerializedName("BatteryLevel")
    @Expose
    private Integer batteryLevel;
    @SerializedName("FuelLevel")
    @Expose
    private Integer fuelLevel;
    @SerializedName("Direction")
    @Expose
    private Object direction;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("Signal")
    @Expose
    private Object signal;
    @SerializedName("VehicleNumber")
    @Expose
    private Object vehicleNumber;
    @SerializedName("Stopped")
    @Expose
    private Boolean stopped;
    @SerializedName("TotalMileage")
    @Expose
    private Integer totalMileage;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(Integer fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Object getDirection() {
        return direction;
    }

    public void setDirection(Object direction) {
        this.direction = direction;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getSignal() {
        return signal;
    }

    public void setSignal(Object signal) {
        this.signal = signal;
    }

    public Object getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Object vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Boolean getStopped() {
        return stopped;
    }

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    public Integer getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Integer totalMileage) {
        this.totalMileage = totalMileage;
    }
}
