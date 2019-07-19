package com.guochuang.mimedia.mvp.model;

public class ExchangeConfig {


    /**
     * aaa2ksb : {"minLimit":0,"serviceRate":0,"timesLimit":0,"tips":"string"}
     * ksb2aaa : {"minLimit":0,"serviceRate":0,"timesLimit":0,"tips":"string"}
     * withdrawAAA : {"minLimit":0,"serviceRate":0,"timesLimit":0,"tips":"string"}
     */

    private Aaa2ksbBean aaa2ksb;
    private Ksb2aaaBean ksb2aaa;
    private WithdrawAAABean withdrawAAA;
    private WithdrawQC withdrawQC;

    public WithdrawQC getWithdrawQC() {
        return withdrawQC;
    }

    public void setWithdrawQC(WithdrawQC withdrawQC) {
        this.withdrawQC = withdrawQC;
    }

    public Aaa2ksbBean getAaa2ksb() {
        return aaa2ksb;
    }

    public void setAaa2ksb(Aaa2ksbBean aaa2ksb) {
        this.aaa2ksb = aaa2ksb;
    }

    public Ksb2aaaBean getKsb2aaa() {
        return ksb2aaa;
    }

    public void setKsb2aaa(Ksb2aaaBean ksb2aaa) {
        this.ksb2aaa = ksb2aaa;
    }

    public WithdrawAAABean getWithdrawAAA() {
        return withdrawAAA;
    }

    public void setWithdrawAAA(WithdrawAAABean withdrawAAA) {
        this.withdrawAAA = withdrawAAA;
    }

    public static class Aaa2ksbBean {
        /**
         * minLimit : 0
         * serviceRate : 0
         * timesLimit : 0
         * tips : string
         */

        private int minLimit;
        private double serviceRate;
        private int timesLimit;
        private String tips;

        public int getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(int minLimit) {
            this.minLimit = minLimit;
        }

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public int getTimesLimit() {
            return timesLimit;
        }

        public void setTimesLimit(int timesLimit) {
            this.timesLimit = timesLimit;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public static class Ksb2aaaBean {
        /**
         * minLimit : 0
         * serviceRate : 0
         * timesLimit : 0
         * tips : string
         */

        private int minLimit;
        private double serviceRate;
        private int timesLimit;
        private String tips;

        public int getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(int minLimit) {
            this.minLimit = minLimit;
        }

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public int getTimesLimit() {
            return timesLimit;
        }

        public void setTimesLimit(int timesLimit) {
            this.timesLimit = timesLimit;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public static class WithdrawAAABean {
        /**
         * minLimit : 0
         * serviceRate : 0
         * timesLimit : 0
         * tips : string
         */

        private int minLimit;
        private double serviceRate;
        private int timesLimit;
        private String tips;

        public int getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(int minLimit) {
            this.minLimit = minLimit;
        }

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public int getTimesLimit() {
            return timesLimit;
        }

        public void setTimesLimit(int timesLimit) {
            this.timesLimit = timesLimit;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public static class WithdrawQC {
        /**
         * minLimit : 0
         * serviceRate : 0
         * timesLimit : 0
         * tips : string
         */

        private double minLimit;
        private double serviceRate;
        private int timesLimit;
        private String tips;

        public double getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(double minLimit) {
            this.minLimit = minLimit;
        }

        public double getServiceRate() {
            return serviceRate;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public int getTimesLimit() {
            return timesLimit;
        }

        public void setTimesLimit(int timesLimit) {
            this.timesLimit = timesLimit;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

}
