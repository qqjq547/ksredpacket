package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class NestTimeInfo {


    /**
     * buyList : [{"auctionDate":"2019-03-09T07:04:17.906Z","dayCoin":0,"dayPrice":0,"endDate":"2019-03-09T07:04:17.906Z","nestLocationId":0,"startDate":"2019-03-09T07:04:17.906Z","totalCoin":0,"totalPrice":0,"userAccountUuid":"string","userAvatar":"string","userMobile":"string","userName":"string","userRealName":"string"}]
     * currentPrice : 0
     * maxPrice : 0
     * startDate : string
     */

    private int currentPrice;
    private int maxPrice;
    private String startDate;
    private List<BuyListBean> buyList;

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<BuyListBean> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<BuyListBean> buyList) {
        this.buyList = buyList;
    }

    public static class BuyListBean {
        /**
         * auctionDate : 2019-03-09T07:04:17.906Z
         * dayCoin : 0
         * dayPrice : 0
         * endDate : 2019-03-09T07:04:17.906Z
         * nestLocationId : 0
         * startDate : 2019-03-09T07:04:17.906Z
         * totalCoin : 0
         * totalPrice : 0
         * userAccountUuid : string
         * userAvatar : string
         * userMobile : string
         * userName : string
         * userRealName : string
         */

        private String auctionDate;
        private int dayCoin;
        private int dayPrice;
        private String endDate;
        private long nestLocationId;
        private String startDate;
        private int totalCoin;
        private int totalPrice;
        private String userAccountUuid;
        private String userAvatar;
        private String userMobile;
        private String userName;
        private String userRealName;

        public String getAuctionDate() {
            return auctionDate;
        }

        public void setAuctionDate(String auctionDate) {
            this.auctionDate = auctionDate;
        }

        public int getDayCoin() {
            return dayCoin;
        }

        public void setDayCoin(int dayCoin) {
            this.dayCoin = dayCoin;
        }

        public int getDayPrice() {
            return dayPrice;
        }

        public void setDayPrice(int dayPrice) {
            this.dayPrice = dayPrice;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public long getNestLocationId() {
            return nestLocationId;
        }

        public void setNestLocationId(long nestLocationId) {
            this.nestLocationId = nestLocationId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getTotalCoin() {
            return totalCoin;
        }

        public void setTotalCoin(int totalCoin) {
            this.totalCoin = totalCoin;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUserAccountUuid() {
            return userAccountUuid;
        }

        public void setUserAccountUuid(String userAccountUuid) {
            this.userAccountUuid = userAccountUuid;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }
    }
}
