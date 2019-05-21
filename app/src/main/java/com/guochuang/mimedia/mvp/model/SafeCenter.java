package com.guochuang.mimedia.mvp.model;

public class SafeCenter {

    /**
     * alipayAccountInfo : {"alipayAccount":"string","alipayRealName":"string"}
     * myBankCardCount : 0
     *
     * nameAuthInfo : {"idCard":"string","idCardPicture":"string","realName":"string"}
     */

    private AlipayAccountInfoBean alipayAccountInfo;
    private int myBankCardCount;
    private int hasWechat;
    private String  mobile;
    private String email;
    private NameAuthInfoBean nameAuthInfo;

    public int getHasWechat() {
        return hasWechat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasWechat(int hasWechat) {
        this.hasWechat = hasWechat;
    }


    public AlipayAccountInfoBean getAlipayAccountInfo() {
        return alipayAccountInfo;
    }

    public void setAlipayAccountInfo(AlipayAccountInfoBean alipayAccountInfo) {
        this.alipayAccountInfo = alipayAccountInfo;
    }

    public int getMyBankCardCount() {
        return myBankCardCount;
    }

    public void setMyBankCardCount(int myBankCardCount) {
        this.myBankCardCount = myBankCardCount;
    }

    public NameAuthInfoBean getNameAuthInfo() {
        return nameAuthInfo;
    }

    public void setNameAuthInfo(NameAuthInfoBean nameAuthInfo) {
        this.nameAuthInfo = nameAuthInfo;
    }

    public static class AlipayAccountInfoBean {
        /**
         * alipayAccount : string
         * alipayRealName : string
         */

        private String alipayAccount;
        private String alipayRealName;

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
    }

    public static class NameAuthInfoBean {
        /**
         * idCard : string
         * idCardPicture : string
         * realName : string
         */

        private String idCard;
        private String idCardPicture;
        private String realName;
        private int auditStatus;
        private String auditDescription;

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

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getAuditDescription() {
            return auditDescription;
        }

        public void setAuditDescription(String auditDescription) {
            this.auditDescription = auditDescription;
        }
    }
}
