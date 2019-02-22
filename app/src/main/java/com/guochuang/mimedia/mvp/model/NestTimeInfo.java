package com.guochuang.mimedia.mvp.model;

public class NestTimeInfo {

    /**
     * current : {"day":0,"endTime":"2019-02-22T05:50:50.664Z","price":0,"sale":true,"saleStartTime":"2019-02-22T05:50:50.664Z","startTime":"2019-02-22T05:50:50.664Z"}
     * ksb : 0
     * nest : {"day":0,"endTime":"2019-02-22T05:50:50.664Z","price":0,"sale":true,"saleStartTime":"2019-02-22T05:50:50.664Z","startTime":"2019-02-22T05:50:50.664Z"}
     * rate : string
     */

    private CurrentBean current;
    private double ksb;
    private NestBean nest;
    private String rate;

    public CurrentBean getCurrent() {
        return current;
    }

    public void setCurrent(CurrentBean current) {
        this.current = current;
    }

    public double getKsb() {
        return ksb;
    }

    public void setKsb(double ksb) {
        this.ksb = ksb;
    }

    public NestBean getNest() {
        return nest;
    }

    public void setNest(NestBean nest) {
        this.nest = nest;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public static class CurrentBean {
        /**
         * day : 0
         * endTime : 2019-02-22T05:50:50.664Z
         * price : 0
         * sale : true
         * saleStartTime : 2019-02-22T05:50:50.664Z
         * startTime : 2019-02-22T05:50:50.664Z
         */

        private int day;
        private String endTime;
        private int price;
        private boolean sale;
        private String saleStartTime;
        private String startTime;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isSale() {
            return sale;
        }

        public void setSale(boolean sale) {
            this.sale = sale;
        }

        public String getSaleStartTime() {
            return saleStartTime;
        }

        public void setSaleStartTime(String saleStartTime) {
            this.saleStartTime = saleStartTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }

    public static class NestBean {
        /**
         * day : 0
         * endTime : 2019-02-22T05:50:50.664Z
         * price : 0
         * sale : true
         * saleStartTime : 2019-02-22T05:50:50.664Z
         * startTime : 2019-02-22T05:50:50.664Z
         */

        private int day;
        private String endTime;
        private int price;
        private boolean sale;
        private String saleStartTime;
        private String startTime;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isSale() {
            return sale;
        }

        public void setSale(boolean sale) {
            this.sale = sale;
        }

        public String getSaleStartTime() {
            return saleStartTime;
        }

        public void setSaleStartTime(String saleStartTime) {
            this.saleStartTime = saleStartTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
