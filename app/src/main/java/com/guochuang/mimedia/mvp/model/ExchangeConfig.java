package com.guochuang.mimedia.mvp.model;

public class ExchangeConfig {

    /**
     * minAAA2KSB : 0
     * minKSB2AAA : 0
     * minWithdrawAAA : 0
     * rateKSB2AAA : 0
     * rateWithdrawAAA : 0
     * tipKSB2AAA : string
     * tipWithdrawAAA : string
     */

    private double minAAA2KSB;
    private double minKSB2AAA;
    private double minWithdrawAAA;
    private double rateKSB2AAA;
    private double rateWithdrawAAA;
    private String tipKSB2AAA;
    private String tipWithdrawAAA;

    public double getMinAAA2KSB() {
        return minAAA2KSB;
    }

    public void setMinAAA2KSB(double minAAA2KSB) {
        this.minAAA2KSB = minAAA2KSB;
    }

    public double getMinKSB2AAA() {
        return minKSB2AAA;
    }

    public void setMinKSB2AAA(double minKSB2AAA) {
        this.minKSB2AAA = minKSB2AAA;
    }

    public double getMinWithdrawAAA() {
        return minWithdrawAAA;
    }

    public void setMinWithdrawAAA(double minWithdrawAAA) {
        this.minWithdrawAAA = minWithdrawAAA;
    }

    public double getRateKSB2AAA() {
        return rateKSB2AAA;
    }

    public void setRateKSB2AAA(double rateKSB2AAA) {
        this.rateKSB2AAA = rateKSB2AAA;
    }

    public double getRateWithdrawAAA() {
        return rateWithdrawAAA;
    }

    public void setRateWithdrawAAA(double rateWithdrawAAA) {
        this.rateWithdrawAAA = rateWithdrawAAA;
    }

    public String getTipKSB2AAA() {
        return tipKSB2AAA;
    }

    public void setTipKSB2AAA(String tipKSB2AAA) {
        this.tipKSB2AAA = tipKSB2AAA;
    }

    public String getTipWithdrawAAA() {
        return tipWithdrawAAA;
    }

    public void setTipWithdrawAAA(String tipWithdrawAAA) {
        this.tipWithdrawAAA = tipWithdrawAAA;
    }
}
