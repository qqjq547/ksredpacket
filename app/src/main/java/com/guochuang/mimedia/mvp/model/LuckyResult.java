package com.guochuang.mimedia.mvp.model;

public class LuckyResult {

    /**
     * coin : string
     * drawSuccess : true
     * money : string
     * reason : string
     */

    private String coin;
    private boolean drawSuccess;
    private String money;
    private String reason;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public boolean isDrawSuccess() {
        return drawSuccess;
    }

    public void setDrawSuccess(boolean drawSuccess) {
        this.drawSuccess = drawSuccess;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
