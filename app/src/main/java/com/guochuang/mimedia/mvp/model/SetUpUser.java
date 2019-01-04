package com.guochuang.mimedia.mvp.model;

public class SetUpUser {

    /**
     * avatar : string
     * contribution : 0
     * inviteCode : string
     * myInviter : string
     * nickName : string
     */

    private String avatar;
    private int contribution;
    private String inviteCode;
    private String myInviter;
    private String nickName;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMyInviter() {
        return myInviter;
    }

    public void setMyInviter(String myInviter) {
        this.myInviter = myInviter;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
