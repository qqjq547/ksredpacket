package com.guochuang.mimedia.mvp.model;

public class NameAuthGet {

    /**
     * idCard : string
     * idCardPicture : string
     * realName : string
     */

    private String idCard;
    private String idCardPicture;
    private String realName;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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
