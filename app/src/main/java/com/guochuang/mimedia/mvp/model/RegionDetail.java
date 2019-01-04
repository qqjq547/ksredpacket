package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class RegionDetail {

    /**
     * avatar : string
     * bidding : true
     * biddingPrice : string
     * biddingTime : string
     * content : string
     * dereliction : true
     * error : string
     * nickName : string
     * picture : ["string"]
     * regionId : 0
     * url : string
     * urlName : string
     * whereRegion : string
     */

    private String avatar;
    private boolean bidding;
    private String biddingPrice;
    private String biddingTime;
    private String content;
    private boolean dereliction;
    private String error;
    private String nickName;
    private long regionId;
    private String url;
    private String urlName;
    private String whereRegion;
    private String explain;
    private List<String> picture;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isBidding() {
        return bidding;
    }

    public void setBidding(boolean bidding) {
        this.bidding = bidding;
    }

    public String getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(String biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public String getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(String biddingTime) {
        this.biddingTime = biddingTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDereliction() {
        return dereliction;
    }

    public void setDereliction(boolean dereliction) {
        this.dereliction = dereliction;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getWhereRegion() {
        return whereRegion;
    }

    public void setWhereRegion(String whereRegion) {
        this.whereRegion = whereRegion;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
