package com.guochuang.mimedia.mvp.model;

public class RecommedUser {

    /**
     * bonus : string
     * nickName : string
     * registerDate : 2018-12-10T05:37:00.475Z
     */

    private String bonus;
    private String nickName;
    private String registerDate;
    private String mobile;
    private String avatar;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
