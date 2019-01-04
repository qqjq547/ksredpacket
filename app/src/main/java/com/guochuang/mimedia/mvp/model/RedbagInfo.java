package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class RedbagInfo {

    /**
     * drawCoin : string
     * microblog : string
     * receiveUserAvatar : ["string"]
     * redPacketContent : string
     * redPacketPicture : string
     * redPacketUuid : string
     * senderAvatar : string
     * senderNickName : string
     * senderUuid : string
     * url : string
     * urlName : string
     * wechat : string
     */

    private String drawCoin;
    private String microblog;
    private String redPacketContent;
    private String redPacketUuid;
    private String senderAvatar;
    private String senderNickName;
    private String senderUuid;
    private String url;
    private String urlName;
    private String wechat;
    private String area;
    private String money;
    private String receiveUserNum;
    private String redPacketTotal;
    private List<String> receiveUserAvatar;
    private List<PictureBean> picture;

    public String getDrawCoin() {
        return drawCoin;
    }

    public void setDrawCoin(String drawCoin) {
        this.drawCoin = drawCoin;
    }

    public String getMicroblog() {
        return microblog;
    }

    public void setMicroblog(String microblog) {
        this.microblog = microblog;
    }

    public String getRedPacketContent() {
        return redPacketContent;
    }

    public void setRedPacketContent(String redPacketContent) {
        this.redPacketContent = redPacketContent;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderNickName() {
        return senderNickName;
    }

    public void setSenderNickName(String senderNickName) {
        this.senderNickName = senderNickName;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getReceiveUserNum() {
        return receiveUserNum;
    }

    public void setReceiveUserNum(String receiveUserNum) {
        this.receiveUserNum = receiveUserNum;
    }

    public List<String> getReceiveUserAvatar() {
        return receiveUserAvatar;
    }

    public void setReceiveUserAvatar(List<String> receiveUserAvatar) {
        this.receiveUserAvatar = receiveUserAvatar;
    }

    public String getRedPacketTotal() {
        return redPacketTotal;
    }

    public void setRedPacketTotal(String redPacketTotal) {
        this.redPacketTotal = redPacketTotal;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }
}
