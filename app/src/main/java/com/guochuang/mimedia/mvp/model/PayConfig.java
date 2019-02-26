package com.guochuang.mimedia.mvp.model;

public class PayConfig {
    private String bizType;
    private int wechatPayType;
    private int aliPayType;
    private int ksbPayType;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int getWechatPayType() {
        return wechatPayType;
    }

    public void setWechatPayType(int wechatPayType) {
        this.wechatPayType = wechatPayType;
    }

    public int getAliPayType() {
        return aliPayType;
    }

    public void setAliPayType(int aliPayType) {
        this.aliPayType = aliPayType;
    }

    public int getKsbPayType() {
        return ksbPayType;
    }

    public void setKsbPayType(int ksbPayType) {
        this.ksbPayType = ksbPayType;
    }
}
