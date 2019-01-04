package com.guochuang.mimedia.mvp.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Random;

public class CityBidHall implements MultiItemEntity {

    public static final int EXIST = 0;
    public static final int NONE = 1;

    @Override
    public int getItemType() {
        if (dereliction) {
            return NONE;
        } else {
            return EXIST;
        }
    }

    /**
     * appointmentDate : string
     * createDate : string
     * currentAvatar : string
     * currentMoney : string
     * currentNickName : string
     * formerAvatar : string
     * formerMoney : string
     * formerNickName : string
     * premium : string
     * regionId : 0
     * whereRegion : string
     */

    private String appointmentDate;
    private String createDate;
    private String currentAvatar;
    private String currentMoney;
    private String currentNickName;
    private String formerAvatar;
    private String formerMoney;
    private String formerNickName;
    private String premium;
    private long regionId;
    private String whereRegion;
    private boolean dereliction;

    public boolean isDereliction() {
        return dereliction;
    }

    public void setDereliction(boolean dereliction) {
        this.dereliction = dereliction;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCurrentAvatar() {
        return currentAvatar;
    }

    public void setCurrentAvatar(String currentAvatar) {
        this.currentAvatar = currentAvatar;
    }

    public String getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getCurrentNickName() {
        return currentNickName;
    }

    public void setCurrentNickName(String currentNickName) {
        this.currentNickName = currentNickName;
    }

    public String getFormerAvatar() {
        return formerAvatar;
    }

    public void setFormerAvatar(String formerAvatar) {
        this.formerAvatar = formerAvatar;
    }

    public String getFormerMoney() {
        return formerMoney;
    }

    public void setFormerMoney(String formerMoney) {
        this.formerMoney = formerMoney;
    }

    public String getFormerNickName() {
        return formerNickName;
    }

    public void setFormerNickName(String formerNickName) {
        this.formerNickName = formerNickName;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getWhereRegion() {
        return whereRegion;
    }

    public void setWhereRegion(String whereRegion) {
        this.whereRegion = whereRegion;
    }
}
