package com.guochuang.mimedia.mvp.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserInfo {

    /**
     * alipayAccount : string
     * alipayRealName : string
     * avatar : string
     * birth : 2018-11-27T03:31:52.903Z
     * createDate : 2018-11-27T03:31:52.903Z
     * createUser : string
     * id : 0
     * introduce : string
     * inviteCode : string
     * ip : string
     * mobile : string
     * nickName : string
     * registerDate : 2018-11-27T03:31:52.903Z
     * registerSource : string
     * sex : 0
     * tenantCode : string
     * updateDate : 2018-11-27T03:31:52.903Z
     * updateUser : string
     * userAccountId : 0
     * userAccountUuid : string
     */
    @Id(assignable = true)
    private long id;
    private String alipayAccount="";
    private String alipayRealName;
    private String avatar;
    private String birth;
    private String createDate;
    private String createUser;
    private String introduce;
    private String inviteCode;
    private String ip;
    private String mobile;
    private String nickName;
    private String registerDate;
    private String registerSource;
    private int sex;
    private String tenantCode;
    private String updateDate;
    private String updateUser;
    private long userAccountId;
    private String userAccountUuid;
    private String twoDimensional;
    private String inviteUrl;
    private String email;

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayRealName() {
        return alipayRealName;
    }

    public void setAlipayRealName(String alipayRealName) {
        this.alipayRealName = alipayRealName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getTwoDimensional() {
        return twoDimensional;
    }

    public void setTwoDimensional(String twoDimensional) {
        this.twoDimensional = twoDimensional;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
