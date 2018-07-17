package com.voxtrail.gpstracking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehiclePOJO implements Serializable{
    @SerializedName("VehicleID")
    @Expose
    private Integer vehicleID;
    @SerializedName("VehicleActive")
    @Expose
    private Boolean vehicleActive;
    @SerializedName("VehicleMade")
    @Expose
    private Object vehicleMade;
    @SerializedName("VehicleType")
    @Expose
    private String vehicleType;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Altitude")
    @Expose
    private Object altitude;
    @SerializedName("Time")
    @Expose
    private String time;
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
    @SerializedName("Signal")
    @Expose
    private Object signal;
    @SerializedName("DriverName")
    @Expose
    private Object driverName;
    @SerializedName("ServiceDueDate")
    @Expose
    private String serviceDueDate;
    @SerializedName("LastServiceDate")
    @Expose
    private String lastServiceDate;
    @SerializedName("VehicleNumber")
    @Expose
    private String vehicleNumber;
    @SerializedName("VehicleModel")
    @Expose
    private Object vehicleModel;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("Mileage")
    @Expose
    private Integer mileage;
    @SerializedName("TodaysMileage")
    @Expose
    private Integer todaysMileage;
    @SerializedName("ExpiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("MaxSpeed")
    @Expose
    private Integer maxSpeed;
    @SerializedName("AccidentSensor")
    @Expose
    private Boolean accidentSensor;
    @SerializedName("Fast")
    @Expose
    private Boolean fast;
    @SerializedName("FuelSensor")
    @Expose
    private Boolean fuelSensor;
    @SerializedName("GeoOnlineFence")
    @Expose
    private Boolean geoOnlineFence;
    @SerializedName("OfflineGeoFence")
    @Expose
    private Boolean offlineGeoFence;
    @SerializedName("IgnitionWire")
    @Expose
    private Boolean ignitionWire;
    @SerializedName("MovementAlerts")
    @Expose
    private Boolean movementAlerts;
    @SerializedName("PanicButton")
    @Expose
    private Boolean panicButton;
    @SerializedName("Relay")
    @Expose
    private Boolean relay;
    @SerializedName("SpeedLimit")
    @Expose
    private Integer speedLimit;
    @SerializedName("SpeedWarnings")
    @Expose
    private Boolean speedWarnings;
    @SerializedName("StopLimit")
    @Expose
    private Integer stopLimit;
    @SerializedName("StopTimeGapMinutes")
    @Expose
    private Integer stopTimeGapMinutes;
    @SerializedName("TemperatureSensor")
    @Expose
    private Boolean temperatureSensor;
    @SerializedName("Owner")
    @Expose
    private Object owner;
    @SerializedName("DeviceID")
    @Expose
    private Object deviceID;

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Boolean getVehicleActive() {
        return vehicleActive;
    }

    public void setVehicleActive(Boolean vehicleActive) {
        this.vehicleActive = vehicleActive;
    }

    public Object getVehicleMade() {
        return vehicleMade;
    }

    public void setVehicleMade(Object vehicleMade) {
        this.vehicleMade = vehicleMade;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Object getAltitude() {
        return altitude;
    }

    public void setAltitude(Object altitude) {
        this.altitude = altitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Object getSignal() {
        return signal;
    }

    public void setSignal(Object signal) {
        this.signal = signal;
    }

    public Object getDriverName() {
        return driverName;
    }

    public void setDriverName(Object driverName) {
        this.driverName = driverName;
    }

    public String getServiceDueDate() {
        return serviceDueDate;
    }

    public void setServiceDueDate(String serviceDueDate) {
        this.serviceDueDate = serviceDueDate;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Object getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(Object vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getTodaysMileage() {
        return todaysMileage;
    }

    public void setTodaysMileage(Integer todaysMileage) {
        this.todaysMileage = todaysMileage;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Boolean getAccidentSensor() {
        return accidentSensor;
    }

    public void setAccidentSensor(Boolean accidentSensor) {
        this.accidentSensor = accidentSensor;
    }

    public Boolean getFast() {
        return fast;
    }

    public void setFast(Boolean fast) {
        this.fast = fast;
    }

    public Boolean getFuelSensor() {
        return fuelSensor;
    }

    public void setFuelSensor(Boolean fuelSensor) {
        this.fuelSensor = fuelSensor;
    }

    public Boolean getGeoOnlineFence() {
        return geoOnlineFence;
    }

    public void setGeoOnlineFence(Boolean geoOnlineFence) {
        this.geoOnlineFence = geoOnlineFence;
    }

    public Boolean getOfflineGeoFence() {
        return offlineGeoFence;
    }

    public void setOfflineGeoFence(Boolean offlineGeoFence) {
        this.offlineGeoFence = offlineGeoFence;
    }

    public Boolean getIgnitionWire() {
        return ignitionWire;
    }

    public void setIgnitionWire(Boolean ignitionWire) {
        this.ignitionWire = ignitionWire;
    }

    public Boolean getMovementAlerts() {
        return movementAlerts;
    }

    public void setMovementAlerts(Boolean movementAlerts) {
        this.movementAlerts = movementAlerts;
    }

    public Boolean getPanicButton() {
        return panicButton;
    }

    public void setPanicButton(Boolean panicButton) {
        this.panicButton = panicButton;
    }

    public Boolean getRelay() {
        return relay;
    }

    public void setRelay(Boolean relay) {
        this.relay = relay;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Boolean getSpeedWarnings() {
        return speedWarnings;
    }

    public void setSpeedWarnings(Boolean speedWarnings) {
        this.speedWarnings = speedWarnings;
    }

    public Integer getStopLimit() {
        return stopLimit;
    }

    public void setStopLimit(Integer stopLimit) {
        this.stopLimit = stopLimit;
    }

    public Integer getStopTimeGapMinutes() {
        return stopTimeGapMinutes;
    }

    public void setStopTimeGapMinutes(Integer stopTimeGapMinutes) {
        this.stopTimeGapMinutes = stopTimeGapMinutes;
    }

    public Boolean getTemperatureSensor() {
        return temperatureSensor;
    }

    public void setTemperatureSensor(Boolean temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Object deviceID) {
        this.deviceID = deviceID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
