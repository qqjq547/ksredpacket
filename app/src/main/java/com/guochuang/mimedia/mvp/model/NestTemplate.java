package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class NestTemplate {

    /**
     * address : string
     * addressDetail : string
     * addressLat : 0
     * addressLng : 0
     * contactPhone : string
     * coverPicture : string
     * introduction : string
     * linkText : string
     * linkUrl : string
     * pictureList : ["string"]
     * shortMsg : string
     * title : string
     * wechat : string
     * weibo : string
     */

    private String address;
    private String addressDetail;
    private double addressLat;
    private double addressLng;
    private String contactPhone;
    private String coverPicture;
    private String introduction;
    private String linkText;
    private String linkUrl;
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

    public double getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(double addressLat) {
        this.addressLat = addressLat;
    }

    public double getAddressLng() {
        return addressLng;
    }

    public void setAddressLng(double addressLng) {
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
