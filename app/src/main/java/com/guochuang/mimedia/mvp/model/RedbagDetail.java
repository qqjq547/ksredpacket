package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;
import java.util.List;

public class RedbagDetail implements Serializable {

    /**
     * areaType : string
     * avatar : string
     * coin : string
     * content : string
     * drawAvatar : ["string"]
     * drawNumber : 0
     * drawSuccess : true
     * microblog : string
     * money : string
     * nickName : string
     * picture : [{"content":"string","jumpUrl":"string","picture":"string","title":"string"}]
     * readingSecond : 0
     * reason : string
     * url : string
     * urlName : string
     * wechat : string
     */

    private String areaType;
    private String avatar;
    private String coin;
    private String content;
    private String drawNumber;
    private boolean drawSuccess=true;
    private String microblog;
    private String money;
    private String nickName;
    private int readingSecond;
    private String reason;
    private String url;
    private String urlName;
    private String wechat;
    private int quantity;
    private String coverUrl;
    private String videoUrl;
    private long surveyId;
    private List<String> drawAvatar;
    private List<PictureBean> picture;
    private List<RedbagAd> systemAd;


    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public boolean isDrawSuccess() {
        return drawSuccess;
    }

    public void setDrawSuccess(boolean drawSuccess) {
        this.drawSuccess = drawSuccess;
    }

    public String getMicroblog() {
        return microblog;
    }

    public void setMicroblog(String microblog) {
        this.microblog = microblog;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getReadingSecond() {
        return readingSecond;
    }

    public void setReadingSecond(int readingSecond) {
        this.readingSecond = readingSecond;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public List<String> getDrawAvatar() {
        return drawAvatar;
    }

    public void setDrawAvatar(List<String> drawAvatar) {
        this.drawAvatar = drawAvatar;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }

    public List<RedbagAd> getSystemAd() {
        return systemAd;
    }

    public void setSystemAd(List<RedbagAd> systemAd) {
        this.systemAd = systemAd;
    }
}
