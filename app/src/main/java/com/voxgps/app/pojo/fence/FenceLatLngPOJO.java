package com.voxgps.app.pojo.fence;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FenceLatLngPOJO implements Serializable{
    @SerializedName("Latitude")
    double Latitude;
    @SerializedName("Longitude")
    double Longitude;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
