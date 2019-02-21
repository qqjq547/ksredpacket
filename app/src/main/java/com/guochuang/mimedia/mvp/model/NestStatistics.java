package com.guochuang.mimedia.mvp.model;

public class NestStatistics {

    /**
     * adNumber : 0
     * clickQuantity : 0
     * countMoney : string
     * favoriteQuantity : 0
     * putIn : 0
     * showQuantity : 0
     */

    private int adNumber;
    private int clickQuantity;
    private String countMoney;
    private int favoriteQuantity;
    private int putIn;
    private int showQuantity;

    public int getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(int adNumber) {
        this.adNumber = adNumber;
    }

    public int getClickQuantity() {
        return clickQuantity;
    }

    public void setClickQuantity(int clickQuantity) {
        this.clickQuantity = clickQuantity;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public int getFavoriteQuantity() {
        return favoriteQuantity;
    }

    public void setFavoriteQuantity(int favoriteQuantity) {
        this.favoriteQuantity = favoriteQuantity;
    }

    public int getPutIn() {
        return putIn;
    }

    public void setPutIn(int putIn) {
        this.putIn = putIn;
    }

    public int getShowQuantity() {
        return showQuantity;
    }

    public void setShowQuantity(int showQuantity) {
        this.showQuantity = showQuantity;
    }
}
