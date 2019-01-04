package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class KsbTransfer {

    /**
     * dailyWithdrawalsTimes : 0
     * increase : 0
     * minWithdrawals : 0
     * payTypeList : [{"account":"string","icon":"string","id":0,"name":"string","payType":0}]
     * poundageRate : 0
     * surplusTimes : 0
     */

    private int dailyWithdrawalsTimes;
    private double increase;
    private String minWithdrawals;
    private double poundageRate;
    private int surplusTimes;
    private String instruction;
    private List<PayTypeListBean> payTypeList;

    public int getDailyWithdrawalsTimes() {
        return dailyWithdrawalsTimes;
    }

    public void setDailyWithdrawalsTimes(int dailyWithdrawalsTimes) {
        this.dailyWithdrawalsTimes = dailyWithdrawalsTimes;
    }

    public double getIncrease() {
        return increase;
    }

    public void setIncrease(double increase) {
        this.increase = increase;
    }

    public String getMinWithdrawals() {
        return minWithdrawals;
    }

    public void setMinWithdrawals(String minWithdrawals) {
        this.minWithdrawals = minWithdrawals;
    }

    public double getPoundageRate() {
        return poundageRate;
    }

    public void setPoundageRate(double poundageRate) {
        this.poundageRate = poundageRate;
    }

    public int getSurplusTimes() {
        return surplusTimes;
    }

    public void setSurplusTimes(int surplusTimes) {
        this.surplusTimes = surplusTimes;
    }

    public List<PayTypeListBean> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<PayTypeListBean> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public static class PayTypeListBean {
        /**
         * account : string
         * icon : string
         * id : 0
         * name : string
         * payType : 0
         */

        private String account;
        private String icon;
        private String id;
        private String name;
        private int payType;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }
    }
}
