package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class Square {

    /**
     * avatar : string
     * content : string
     * nickName : string
     * picture : ["string"]
     * redPacketUuid : string
     */

    private String avatar;
    private String content;
    private String nickName;
    private String redPacketUuid;
    private List<String> picture;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
