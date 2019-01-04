package com.guochuang.mimedia.mvp.model;

public class DrawStatistics {

    /**
     * tenantCode : string
     * totalCoin : 0
     * totalCount : 0
     * userAccountUuid : string
     */

    private String tenantCode;
    private double totalCoin;
    private int totalCount;
    private String userAccountUuid;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public double getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(double totalCoin) {
        this.totalCoin = totalCoin;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
