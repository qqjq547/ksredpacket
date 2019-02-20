package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class NestAd {

    /**
     * address : string
     * addressDetail : string
     * addressLat : 0
     * addressLng : 0
     * contactPhone : string
     * coverPicture : string
     * introduction : string
     * isCollection : 0
     * linkText : string
     * linkUrl : string
     * nestInfoId : 0
     * nestLocationId : 0
     * nestLocationLat : 0
     * nestLocationLng : 0
     * nestTemplateId : 0
     * nestTimeInfoId : 0
     * pictureList : ["string"]
     * shortMsg : string
     * title : string
     * wechat : string
     * weibo : string
     */

    private String address;
    private String addressDetail;
    private int addressLat;
    private int addressLng;
    private String contactPhone;
    private String coverPicture;
    private String introduction;
    private int isCollection;
    private String linkText;
    private String linkUrl;
    private long nestInfoId;
    private long nestLocationId;
    private String nestLocationLat;
    private String nestLocationLng;
    private long nestTemplateId;
    private long nestTimeInfoId;
    private String shortMsg;
    private String title;
    private String wechat;
    private String weibo;
    private List<String> pictureList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public int getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(int addressLat) {
        this.addressLat = addressLat;
    }

    public int getAddressLng() {
        return addressLng;
    }

    public void setAddressLng(int addressLng) {
        this.addressLng = addressLng;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public String getNestLocationLat() {
        return nestLocationLat;
    }

    public void setNestLocationLat(String nestLocationLat) {
        this.nestLocationLat = nestLocationLat;
    }

    public String getNestLocationLng() {
        return nestLocationLng;
    }

    public void setNestLocationLng(String nestLocationLng) {
        this.nestLocationLng = nestLocationLng;
    }

    public long getNestTemplateId() {
        return nestTemplateId;
    }

    public void setNestTemplateId(long nestTemplateId) {
        this.nestTemplateId = nestTemplateId;
    }

    public long getNestTimeInfoId() {
        return nestTimeInfoId;
    }

    public void setNestTimeInfoId(long nestTimeInfoId) {
        this.nestTimeInfoId = nestTimeInfoId;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }
}
