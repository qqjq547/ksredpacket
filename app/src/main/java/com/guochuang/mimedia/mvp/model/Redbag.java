package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;

public class Redbag implements Serializable {


    /**
     * latitude : 22.631316
     * longitude : 113.818066
     * uuid : 587476fcfcb541e1995b57fc7d1ded0f
     * avatar : http://imge.kugou.com/stdmusic/20181115/20181115140839935228.jpg
     * nickName : 快收
     * bestWishes : 给你发了一个系统红包
     * type : random
     * roleType : system
     * password : null
     */

    private String latitude;
    private String longitude;
    private String uuid;
    private String avatar;
    private String nickName;
    private String bestWishes;
    private String type;
    private String roleType;
    private String password;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getBestWishes() {
        return bestWishes;
    }

    public void setBestWishes(String bestWishes) {
        this.bestWishes = bestWishes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Redbag{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", uuid='" + uuid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", bestWishes='" + bestWishes + '\'' +
                ", type='" + type + '\'' +
                ", roleType='" + roleType + '\'' +
                ", password=" + password +
                '}';
    }
}
