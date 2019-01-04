package com.guochuang.mimedia.mvp.model;

public class RainRecord {

    /**
     * drawCoin : 0
     * expireTime : string
     * redPacketRainUuid : string
     * state : 0
     * title : string
     */

    private String drawCoin;
    private String expireTime;
    private String redPacketRainUuid;
    private int state;
    private String title;
    private int quantity;
    private int validSecond ;

    public String getDrawCoin() {
        return drawCoin;
    }

    public void setDrawCoin(String drawCoin) {
        this.drawCoin = drawCoin;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getRedPacketRainUuid() {
        return redPacketRainUuid;
    }

    public void setRedPacketRainUuid(String redPacketRainUuid) {
        this.redPacketRainUuid = redPacketRainUuid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValidSecond() {
        return validSecond;
    }

    public void setValidSecond(int validSecond) {
        this.validSecond = validSecond;
    }
}
