package com.guochuang.mimedia.mvp.model;

public class PayCode {

    /**
     * mobile : string
     * nickName : string
     * qrcode : string
     * realName : string
     * userAccountUuid : string
     */

    private String mobile;
    private String nickName;
    private String qrcode;
    private String realName;
    private String userAccountUuid;

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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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
}
