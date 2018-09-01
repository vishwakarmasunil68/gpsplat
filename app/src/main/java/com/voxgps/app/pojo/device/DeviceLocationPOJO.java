package com.voxgps.app.pojo.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceLocationPOJO {
    @SerializedName("dt_server")
    @Expose
    private String dtServer;
    @SerializedName("dt_tracker")
    @Expose
    private String dtTracker;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("altitude")
    @Expose
    private String altitude;
    @SerializedName("angle")
    @Expose
    private String angle;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("params")
    @Expose
    private String params;

    public String getDtServer() {
        return dtServer;
    }

    public void setDtServer(String dtServer) {
        this.dtServer = dtServer;
    }

    public String getDtTracker() {
        return dtTracker;
    }

    public void setDtTracker(String dtTracker) {
        this.dtTracker = dtTracker;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DeviceLocationPOJO{" +
                "dtServer='" + dtServer + '\'' +
                ", dtTracker='" + dtTracker + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", altitude='" + altitude + '\'' +
                ", angle='" + angle + '\'' +
                ", speed='" + speed + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
