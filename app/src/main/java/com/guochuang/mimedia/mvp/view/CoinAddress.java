package com.guochuang.mimedia.mvp.view;

import java.io.Serializable;

public class CoinAddress implements Serializable {

    /**
     * addressBookId : 0
     * alias : string
     * chainAddress : string
     * chainType : 0
     * remark : string
     */

    private long addressBookId;
    private String alias;
    private String chainAddress;
    private int chainType;
    private String remark;

    public long getAddressBookId() {
        return addressBookId;
    }

    public void setAddressBookId(long addressBookId) {
        this.addressBookId = addressBookId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getChainAddress() {
        return chainAddress;
    }

    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
    }

    public int getChainType() {
        return chainType;
    }

    public void setChainType(int chainType) {
        this.chainType = chainType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
