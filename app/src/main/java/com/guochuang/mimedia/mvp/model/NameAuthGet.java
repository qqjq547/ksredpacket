package com.guochuang.mimedia.mvp.model;

public class NameAuthGet {


    /**
     * auditDescription : string
     * auditStatus : 0
     * idCard : string
     * idCardBackPicture : string
     * idCardPicture : string
     * realName : string
     */

    private String auditDescription;
    private int auditStatus;
    private String idCard;
    private String idCardBackPicture;
    private String idCardPicture;
    private String realName;

    public String getAuditDescription() {
        return auditDescription;
    }

    public void setAuditDescription(String auditDescription) {
        this.auditDescription = auditDescription;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardBackPicture() {
        return idCardBackPicture;
    }

    public void setIdCardBackPicture(String idCardBackPicture) {
        this.idCardBackPicture = idCardBackPicture;
    }

    public String getIdCardPicture() {
        return idCardPicture;
    }

    public void setIdCardPicture(String idCardPicture) {
        this.idCardPicture = idCardPicture;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
