package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;

public class PaymentResult implements Serializable {

    /**
     * receiptPayStatus : 1
     * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Vm5JnFicdicfIcZRBPfaF0OpszogA86wnOicMw8sxLW1ur2EA8Dcw0wNtHIgnoIiaDLAevu3kkUCMGiaib2En3ERRqIQ/132
     * nickName : 十三
     * realName : *波
     * mobile : 183****4761
     * userAccountUuid : e95ff1115da84adc93c9602914b635fd
     * money : 1.0000
     * coin : 10.0
     * tipsUrl : http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/default/f5cc15b774444dc1b822568d4a7297f7.png
     * tipsjumpUrl :
     */

    private int receiptPayStatus;
    private String avatar;
    private String nickName;
    private String realName;
    private String mobile;
    private String userAccountUuid;
    private String money;
    private String coin;
    private String tipsUrl;
    private String tipsjumpUrl;

    public int getReceiptPayStatus() {
        return receiptPayStatus;
    }

    public void setReceiptPayStatus(int receiptPayStatus) {
        this.receiptPayStatus = receiptPayStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getTipsUrl() {
        return tipsUrl;
    }

    public void setTipsUrl(String tipsUrl) {
        this.tipsUrl = tipsUrl;
    }

    public String getTipsjumpUrl() {
        return tipsjumpUrl;
    }

    public void setTipsjumpUrl(String tipsjumpUrl) {
        this.tipsjumpUrl = tipsjumpUrl;
    }
}
