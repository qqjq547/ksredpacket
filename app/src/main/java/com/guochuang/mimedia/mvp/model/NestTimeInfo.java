package com.guochuang.mimedia.mvp.model;

public class NestTimeInfo {


    /**
     * current : {"day":0,"endTime":"2019-02-23T06:30:29.606Z","nestTimeInfoId":0,"nextTime":"2019-02-23T06:30:29.606Z","price":0,"sale":true,"saleStartTime":"2019-02-23T06:30:29.606Z","startTime":"2019-02-23T06:30:29.606Z","totalPrice":0,"userAvatar":"string","userName":"string"}
     * ksb : 0
     * next : {"day":0,"endTime":"2019-02-23T06:30:29.606Z","nestTimeInfoId":0,"nextTime":"2019-02-23T06:30:29.606Z","price":0,"sale":true,"saleStartTime":"2019-02-23T06:30:29.606Z","startTime":"2019-02-23T06:30:29.606Z","totalPrice":0,"userAvatar":"string","userName":"string"}
     * rate : string
     */

    private CurrentBean current;
    private String ksb;
    private NextBean next;
    private String rate;
    private int maxPrice;

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public CurrentBean getCurrent() {
        return current;
    }

    public void setCurrent(CurrentBean current) {
        this.current = current;
    }

    public String getKsb() {
        return ksb;
    }

    public void setKsb(String ksb) {
        this.ksb = ksb;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
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
         * endTime : 2019-02-23T06:30:29.606Z
         * nestTimeInfoId : 0
         * nextTime : 2019-02-23T06:30:29.606Z
         * price : 0
         * sale : true
         * saleStartTime : 2019-02-23T06:30:29.606Z
         * startTime : 2019-02-23T06:30:29.606Z
         * totalPrice : 0
         * userAvatar : string
         * userName : string
         */

        private int day;
        private String endTime;
        private long nestTimeInfoId;
        private String nextTime;
        private int price;
        private boolean sale;
        private String saleStartTime;
        private String startTime;
        private int totalPrice;
        private String userAvatar;
        private String userName;

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

        public long getNestTimeInfoId() {
            return nestTimeInfoId;
        }

        public void setNestTimeInfoId(long nestTimeInfoId) {
            this.nestTimeInfoId = nestTimeInfoId;
        }

        public String getNextTime() {
            return nextTime;
        }

        public void setNextTime(String nextTime) {
            this.nextTime = nextTime;
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

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class NextBean {
        /**
         * day : 0
         * endTime : 2019-02-23T06:30:29.606Z
         * nestTimeInfoId : 0
         * nextTime : 2019-02-23T06:30:29.606Z
         * price : 0
         * sale : true
         * saleStartTime : 2019-02-23T06:30:29.606Z
         * startTime : 2019-02-23T06:30:29.606Z
         * totalPrice : 0
         * userAvatar : string
         * userName : string
         */

        private int day;
        private String endTime;
        private long nestTimeInfoId;
        private String nextTime;
        private int price;
        private boolean sale;
        private String saleStartTime;
        private String startTime;
        private int totalPrice;
        private String userAvatar;
        private String userName;

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

        public long getNestTimeInfoId() {
            return nestTimeInfoId;
        }

        public void setNestTimeInfoId(long nestTimeInfoId) {
            this.nestTimeInfoId = nestTimeInfoId;
        }

        public String getNextTime() {
            return nextTime;
        }

        public void setNextTime(String nextTime) {
            this.nextTime = nextTime;
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

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
