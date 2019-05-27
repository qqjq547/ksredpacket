package com.guochuang.mimedia.mvp.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SealRecord implements MultiItemEntity {

    /**
     * coin : string
     * createDate : string
     * isIncome : string
     * startIndex : string
     * title : string
     * type : string
     * userAccountUuid : string
     */

    private String coin;
    private String createDate;
    private String isIncome;
    private String startIndex;
    private String title;
    private String type;
    private String userAccountUuid;
    private String remark;
    private String targetAddress;//接收地址
    private String serviceFee;//旷工费
    private int status;//SEAL明细状态
    private String equivalence;//SEAL等值数量
    private String ksbType;
    private String money;



    public String getKsbType() {
        return ksbType;
    }

    public void setKsbType(String ksbType) {
        this.ksbType = ksbType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(String isIncome) {
        this.isIncome = isIncome;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEquivalence() {
        return equivalence;
    }

    public void setEquivalence(String equivalence) {
        this.equivalence = equivalence;
    }


    public static final int SIMPLE = 0;
    public static final int TRANSFER = 1;

    public static final String TYPE_QC2SEAL = "133";
    public static final String TYPE_SEAL2QC = "136";
    public static final String TYPE_EXTRA = "134";
    public static final String TYPE_FILL = "135";

    // 淘区块
    public static final String TYPE_TQK = "123";


    @Override
    public int getItemType() {
        switch (ksbType){
            case TYPE_TQK://淘区块
            case TYPE_QC2SEAL://QC转SEAL
            case TYPE_SEAL2QC://SEAL转QC
            case TYPE_EXTRA://提币
            case TYPE_FILL://充币
                return TRANSFER;
        }
        return SIMPLE;
    }

}
