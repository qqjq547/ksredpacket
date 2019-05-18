package com.guochuang.mimedia.mvp.model;

public class MySeal {


    /**
     * coin : 0.0000
     * ksbPrice : 0.026241
     * money : 0.00
     * ksbAddress : 0xabd8d7725e660bab61c16069abf5fa805d9b5bca
     * digitalCurrency : SEAL
     * nameAuthentication : 1
     * remark : 平台SEAL价格计算方式：\r\n\r\n每分钟获取一次交易所SEAL价格，然后计算近30分钟的平均值作为SEAL价格
     * qrcodeUrlKey : http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/twoDimensional/ede080570dc74feb87e021ae70252530.png
     */

    private String coin;
    private String ksbPrice;
    private String money;
    private String ksbAddress;
    private String digitalCurrency;
    private int nameAuthentication;
    private String remark;
    private String qrcodeUrlKey;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getKsbPrice() {
        return ksbPrice;
    }

    public void setKsbPrice(String ksbPrice) {
        this.ksbPrice = ksbPrice;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getKsbAddress() {
        return ksbAddress;
    }

    public void setKsbAddress(String ksbAddress) {
        this.ksbAddress = ksbAddress;
    }

    public String getDigitalCurrency() {
        return digitalCurrency;
    }

    public void setDigitalCurrency(String digitalCurrency) {
        this.digitalCurrency = digitalCurrency;
    }

    public int getNameAuthentication() {
        return nameAuthentication;
    }

    public void setNameAuthentication(int nameAuthentication) {
        this.nameAuthentication = nameAuthentication;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQrcodeUrlKey() {
        return qrcodeUrlKey;
    }

    public void setQrcodeUrlKey(String qrcodeUrlKey) {
        this.qrcodeUrlKey = qrcodeUrlKey;
    }
}
