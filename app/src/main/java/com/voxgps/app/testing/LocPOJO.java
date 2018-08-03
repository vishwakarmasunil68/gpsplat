package com.voxgps.app.testing;

public class LocPOJO {
    int id;
    String lat;
    String lng;
    String alt;
    String speed;
    String dt;

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

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "LocPOJO{" +
                "id=" + id +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", alt='" + alt + '\'' +
                ", speed='" + speed + '\'' +
                ", dt='" + dt + '\'' +
                '}';
    }
}
