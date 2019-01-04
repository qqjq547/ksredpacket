package com.guochuang.mimedia.mvp.model;

public class RainMsg {

    /**
     * message : string
     * quantity : 0
     * redPacketRainUuid : string
     * validSecond : 0
     * yes : true
     */

    private String message;
    private int quantity;
    private String redPacketRainUuid;
    private int validSecond;
    private boolean yes;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRedPacketRainUuid() {
        return redPacketRainUuid;
    }

    public void setRedPacketRainUuid(String redPacketRainUuid) {
        this.redPacketRainUuid = redPacketRainUuid;
    }

    public int getValidSecond() {
        return validSecond;
    }

    public void setValidSecond(int validSecond) {
        this.validSecond = validSecond;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }
}
