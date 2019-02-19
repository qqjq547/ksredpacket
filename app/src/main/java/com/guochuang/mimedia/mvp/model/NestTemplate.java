package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;
import java.util.List;

public class NestTemplate implements Serializable {

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
    private long nestTemplateId;
    private String address;
    private String addressDetail;
    private String addressLat;
    private String addressLng;
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

    public NestTemplate() {
    }

    public long getNestTemplateId() {
        return nestTemplateId;
    }

    public void setNestTemplateId(long nestTemplateId) {
        this.nestTemplateId = nestTemplateId;
    }

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

    public String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(String addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressLng() {
        return addressLng;
    }

    public void setAddressLng(String addressLng) {
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
