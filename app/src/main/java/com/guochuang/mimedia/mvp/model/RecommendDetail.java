package com.guochuang.mimedia.mvp.model;

public class RecommendDetail {

    /**
     * cumulativeCoin : string
     * cumulativeMoney : string
     * directAgent : 0
     * directUser : 0
     * role : 0
     */

    private String cumulativeCoin;
    private String cumulativeMoney;
    private int directAgent;
    private int directUser;
    private int role;

    public String getCumulativeCoin() {
        return cumulativeCoin;
    }

    public void setCumulativeCoin(String cumulativeCoin) {
        this.cumulativeCoin = cumulativeCoin;
    }

    public String getCumulativeMoney() {
        return cumulativeMoney;
    }

    public void setCumulativeMoney(String cumulativeMoney) {
        this.cumulativeMoney = cumulativeMoney;
    }

    public int getDirectAgent() {
        return directAgent;
    }

    public void setDirectAgent(int directAgent) {
        this.directAgent = directAgent;
    }

    public int getDirectUser() {
        return directUser;
    }

    public void setDirectUser(int directUser) {
        this.directUser = directUser;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
