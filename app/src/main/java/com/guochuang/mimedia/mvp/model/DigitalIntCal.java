package com.guochuang.mimedia.mvp.model;

public class DigitalIntCal {

    /**
     * digitalCoin : string
     * digitalRate : string
     * ksbCoin : string
     * ksbRate : string
     * type : 0
     */

    private String digitalCoin;
    private String digitalRate;
    private String ksbCoin;
    private String ksbRate;
    private int type;

    public String getDigitalCoin() {
        return digitalCoin;
    }

    public void setDigitalCoin(String digitalCoin) {
        this.digitalCoin = digitalCoin;
    }

    public String getDigitalRate() {
        return digitalRate;
    }

    public void setDigitalRate(String digitalRate) {
        this.digitalRate = digitalRate;
    }

    public String getKsbCoin() {
        return ksbCoin;
    }

    public void setKsbCoin(String ksbCoin) {
        this.ksbCoin = ksbCoin;
    }

    public String getKsbRate() {
        return ksbRate;
    }

    public void setKsbRate(String ksbRate) {
        this.ksbRate = ksbRate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
