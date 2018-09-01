package com.voxgps.app.pojo.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DevicePOJO implements Serializable{
    @SerializedName("object_id")
    @Expose
    private String objectId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("trailer_id")
    @Expose
    private String trailerId;
    @SerializedName("object_detail")
    @Expose
    private DeviceDetailPOJO deviceDetailPOJO;


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public DeviceDetailPOJO getDeviceDetailPOJO() {
        return deviceDetailPOJO;
    }

    public void setDeviceDetailPOJO(DeviceDetailPOJO deviceDetailPOJO) {
        this.deviceDetailPOJO = deviceDetailPOJO;
    }

    @Override
    public String toString() {
        return "DevicePOJO{" +
                "objectId='" + objectId + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}
