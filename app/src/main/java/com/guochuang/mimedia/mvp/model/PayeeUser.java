package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;

public class PayeeUser implements Serializable {

    /**
     * coin : string
     * mobile : string
     * nickName : string
     * rate : string
     * realName : string
     * userAccountUuid : string
     */

    private String coin;
    private String mobile;
    private String nickName;
    private String rate;
    private String realName;
    private String avatar;
    private String userAccountUuid;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
