package com.voxgps.app.pojo.fence;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FencePOJO implements Serializable {
    @SerializedName("Address")
    String Address;
    @SerializedName("VehicleID")
    int VehicleID;
    @SerializedName("FencePloygonData")
    String FencePloygonData;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getVehicleID() {
        return VehicleID;
    }

    public void setVehicleID(int vehicleID) {
        VehicleID = vehicleID;
    }

    public String getFencePloygonData() {
        return FencePloygonData;
    }

    public void setFencePloygonData(String fencePloygonData) {
        FencePloygonData = fencePloygonData;
    }
}
