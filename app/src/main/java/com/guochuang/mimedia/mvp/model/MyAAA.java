package com.guochuang.mimedia.mvp.model;

public class MyAAA {

    /**
     * tenantCode : null
     * id : null
     * createUser : null
     * createDate : null
     * updateUser : null
     * updateDate : null
     * walletAddress : 0XAAAAAAAAAAAAA
     * digitalCurrency : AAA
     * coin : 1000.0
     * money : 5.4
     * exchangeRate : 0.005402
     * nameAuthentication : 1
     * qrcodeUrlKey : http://www.baidu.com
     */

    private String tenantCode;
    private String id;
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;
    private String walletAddress;
    private String digitalCurrency;
    private double coin;
    private double money;
    private double exchangeRate;
    private int nameAuthentication;
    private String qrcodeUrlKey;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Object getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getDigitalCurrency() {
        return digitalCurrency;
    }

    public void setDigitalCurrency(String digitalCurrency) {
        this.digitalCurrency = digitalCurrency;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public int getNameAuthentication() {
        return nameAuthentication;
    }

    public void setNameAuthentication(int nameAuthentication) {
        this.nameAuthentication = nameAuthentication;
    }

    public String getQrcodeUrlKey() {
        return qrcodeUrlKey;
    }

    public void setQrcodeUrlKey(String qrcodeUrlKey) {
        this.qrcodeUrlKey = qrcodeUrlKey;
    }
}
