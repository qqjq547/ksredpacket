package com.guochuang.mimedia.mvp.model;

public class NestHomeAd {

    /**
     * coverPicture : string
     * nestInfoId : 0
     * nestLocationId : 0
     * nestTimeId : 0
     * shortMsg : string
     */

    private String coverPicture;
    private long nestInfoId;
    private long nestLocationId;
    private long nestTimeId;
    private String shortMsg;

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public long getNestInfoId() {
        return nestInfoId;
    }

    public void setNestInfoId(long nestInfoId) {
        this.nestInfoId = nestInfoId;
    }

    public long getNestLocationId() {
        return nestLocationId;
    }

    public void setNestLocationId(long nestLocationId) {
        this.nestLocationId = nestLocationId;
    }

    public long getNestTimeId() {
        return nestTimeId;
    }

    public void setNestTimeId(long nestTimeId) {
        this.nestTimeId = nestTimeId;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }
}
