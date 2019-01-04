package com.guochuang.mimedia.mvp.model;

public class CalValue {

    /**
     * coin : 0
     * coinByMoney : 0
     * money : 0
     * moneyByCoin : 0
     * type : 0
     */

    private String coin;
    private String coinByMoney;
    private String money;
    private String moneyByCoin;
    private int type;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCoinByMoney() {
        return coinByMoney;
    }

    public void setCoinByMoney(String coinByMoney) {
        this.coinByMoney = coinByMoney;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyByCoin() {
        return moneyByCoin;
    }

    public void setMoneyByCoin(String moneyByCoin) {
        this.moneyByCoin = moneyByCoin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
