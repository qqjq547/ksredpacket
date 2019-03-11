package com.guochuang.mimedia.mvp.model;

public class NestLocation {

    /**
     * latitude : 0
     * longitude : 0
     * nestLocationId : 0
     */

    private double latitude;
    private double longitude;
    private String avatar;
    private long nestLocationId;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getNestLocationId() {
        return nestLocationId;
    }

    public void setNestLocationId(long nestLocationId) {
        this.nestLocationId = nestLocationId;
    }
}
