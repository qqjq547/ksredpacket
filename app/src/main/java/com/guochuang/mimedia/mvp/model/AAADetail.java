package com.guochuang.mimedia.mvp.model;

public class AAADetail {

    /**
     * tenantCode : gcyh
     * id : 1
     * createUser : null
     * createDate : 2019-04-24 14:14:05
     * updateUser : null
     * updateDate : null
     * userAccountId : 10
     * userAccountUuid : e95ff1115da84adc93c9602914b635fd
     * sourceDigitalCurrency : AAA
     * targetDigitalCurrency : KSB
     * sourceCoin : 10.0
     * targetCoin : 100.0
     * sourceAddress : 0XAAAA
     * targetAddress : 0XBBBB
     * serviceFee : 1.0
     * serviceRate : 1.0
     * status : 1
     * type : 1
     * applyDate : 2019-04-24 14:14:24
     * finishDate : 2019-04-24 14:14:27
     * transferLogId : null
     * remark : 交易完成
     */

    private String tenantCode;
    private int id;
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;
    private int userAccountId;
    private String userAccountUuid;
    private String sourceDigitalCurrency;
    private String targetDigitalCurrency;
    private String sourceCoin;
    private String targetCoin;
    private String sourceAddress;
    private String targetAddress;
    private double serviceFee;
    private double serviceRate;
    private int status;
    private int type;
    private  int businessType;
    private String applyDate;
    private String finishDate;
    private String transferLogId;
    private String remark;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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

    public String getSourceDigitalCurrency() {
        return sourceDigitalCurrency;
    }

    public void setSourceDigitalCurrency(String sourceDigitalCurrency) {
        this.sourceDigitalCurrency = sourceDigitalCurrency;
    }

    public String getTargetDigitalCurrency() {
        return targetDigitalCurrency;
    }

    public void setTargetDigitalCurrency(String targetDigitalCurrency) {
        this.targetDigitalCurrency = targetDigitalCurrency;
    }

    public String getSourceCoin() {
        return sourceCoin;
    }

    public void setSourceCoin(String sourceCoin) {
        this.sourceCoin = sourceCoin;
    }

    public String getTargetCoin() {
        return targetCoin;
    }

    public void setTargetCoin(String targetCoin) {
        this.targetCoin = targetCoin;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getTransferLogId() {
        return transferLogId;
    }

    public void setTransferLogId(String transferLogId) {
        this.transferLogId = transferLogId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
