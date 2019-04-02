package com.guochuang.mimedia.mvp.model;

public class RedBagConfig {

    /**
     * latitude : 0.0
     * longitude : 0.0
     * passwordMinMoney : null
     * passwordMinOneMoney : null
     * surveyMinMoney : 100
     * surveyMinOneMoney : 0.5
     */

    private double latitude;
    private double longitude;
    private double passwordMinMoney;
    private double passwordMinOneMoney;
    private double surveyMinMoney;
    private double surveyMinOneMoney;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPasswordMinMoney() {
        return passwordMinMoney;
    }

    public void setPasswordMinMoney(double passwordMinMoney) {
        this.passwordMinMoney = passwordMinMoney;
    }

    public double getPasswordMinOneMoney() {
        return passwordMinOneMoney;
    }

    public void setPasswordMinOneMoney(double passwordMinOneMoney) {
        this.passwordMinOneMoney = passwordMinOneMoney;
    }

    public double getSurveyMinMoney() {
        return surveyMinMoney;
    }

    public void setSurveyMinMoney(double surveyMinMoney) {
        this.surveyMinMoney = surveyMinMoney;
    }

    public double getSurveyMinOneMoney() {
        return surveyMinOneMoney;
    }

    public void setSurveyMinOneMoney(double surveyMinOneMoney) {
        this.surveyMinOneMoney = surveyMinOneMoney;
    }
}
