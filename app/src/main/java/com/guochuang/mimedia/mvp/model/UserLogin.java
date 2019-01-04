package com.guochuang.mimedia.mvp.model;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserLogin {

    /**
     * sub : sdfadf
     * h-tenant-code : gcyh
     * exp : 1559283379
     * iat : 1542003379384
     */

    @Id
    long id;

    private String sub;
    @SerializedName("h-tenant-code")
    private String htenantcode;
    private int exp;
    private long iat;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getHtenantcode() {
        return htenantcode;
    }

    public void setHtenantcode(String htenantcode) {
        this.htenantcode = htenantcode;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }
}
