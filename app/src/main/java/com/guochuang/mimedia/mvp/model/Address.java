package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;

public class Address implements Serializable {

    /**
     * address : string
     * city : string
     * createDate : 2019-01-22T05:37:15.603Z
     * createUser : string
     * district : string
     * id : 0
     * isDefault : 0
     * isDel : 0
     * province : string
     * sort : 0
     * tenantCode : string
     * trackMobile : string
     * trackName : string
     * updateDate : 2019-01-22T05:37:15.603Z
     * updateUser : string
     * userAccountId : 0
     * userAccountUuid : string
     * uuid : string
     */

    private String address;
    private String city;
    private String createDate;
    private String createUser;
    private String district;
    private long id;
    private int isDefault;
    private int isDel;
    private String province;
    private int sort;
    private String tenantCode;
    private String trackMobile;
    private String trackName;
    private String updateDate;
    private String updateUser;
    private int userAccountId;
    private String userAccountUuid;
    private String uuid;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTrackMobile() {
        return trackMobile;
    }

    public void setTrackMobile(String trackMobile) {
        this.trackMobile = trackMobile;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
