package com.guochuang.mimedia.mvp.model;

public class BidPrice {

    /**
     * coin : string
     * createDate : string
     * type : string
     * whereRegion : string
     */

    private String coin;
    private String principalCoin;
    private String createDate;
    private String type;
    private String whereRegion;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhereRegion() {
        return whereRegion;
    }

    public void setWhereRegion(String whereRegion) {
        this.whereRegion = whereRegion;
    }

    public String getPrincipalCoin() {
        return principalCoin;
    }

    public void setPrincipalCoin(String principalCoin) {
        this.principalCoin = principalCoin;
    }
}
