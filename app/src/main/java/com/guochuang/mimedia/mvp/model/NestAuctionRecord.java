package com.guochuang.mimedia.mvp.model;

public class NestAuctionRecord {

    /**
     * auctionDate : 2019-02-19T07:26:12.902Z
     * auctionNum : 0
     * dayPrice : 0
     * dealNum : 0
     * endDate : 2019-02-19T07:26:12.902Z
     * nestTimeInfoId : 0
     * startDate : 2019-02-19T07:26:12.902Z
     * totalPrice : 0
     * userAvatar : string
     * userName : string
     * userUuid : string
     */

    private String auctionDate;
    private int auctionNum;
    private int dayPrice;
    private int dealNum;
    private String endDate;
    private int nestTimeInfoId;
    private String startDate;
    private int totalPrice;
    private String userAvatar;
    private String userName;
    private String userUuid;

    public String getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(String auctionDate) {
        this.auctionDate = auctionDate;
    }

    public int getAuctionNum() {
        return auctionNum;
    }

    public void setAuctionNum(int auctionNum) {
        this.auctionNum = auctionNum;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public int getDealNum() {
        return dealNum;
    }

    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNestTimeInfoId() {
        return nestTimeInfoId;
    }

    public void setNestTimeInfoId(int nestTimeInfoId) {
        this.nestTimeInfoId = nestTimeInfoId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
