package com.guochuang.mimedia.mvp.model;

public class InviterUser {

    /**
     * avatar : string
     * inviteCode : string
     * nickName : string
     */

    private String avatar;
    private String inviteCode;
    private String nickName;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
