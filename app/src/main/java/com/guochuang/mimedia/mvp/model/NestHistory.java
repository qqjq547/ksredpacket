package com.guochuang.mimedia.mvp.model;

public class NestHistory {

    /**
     * avatar : string
     * endDate : string
     * nestStatisticsResDto : {"clickQuantity":0,"favoriteQuantity":0,"showQuantity":0}
     * nickName : string
     * startDate : string
     * totalPrice : string
     * unitPrice : string
     */

    private String avatar;
    private String endDate;
    private NestStatisticsResDtoBean nestStatisticsResDto;
    private String nickName;
    private String startDate;
    private String totalPrice;
    private String unitPrice;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public NestStatisticsResDtoBean getNestStatisticsResDto() {
        return nestStatisticsResDto;
    }

    public void setNestStatisticsResDto(NestStatisticsResDtoBean nestStatisticsResDto) {
        this.nestStatisticsResDto = nestStatisticsResDto;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static class NestStatisticsResDtoBean {
        /**
         * clickQuantity : 0
         * favoriteQuantity : 0
         * showQuantity : 0
         */

        private int clickQuantity;
        private int favoriteQuantity;
        private int showQuantity;

        public int getClickQuantity() {
            return clickQuantity;
        }

        public void setClickQuantity(int clickQuantity) {
            this.clickQuantity = clickQuantity;
        }

        public int getFavoriteQuantity() {
            return favoriteQuantity;
        }

        public void setFavoriteQuantity(int favoriteQuantity) {
            this.favoriteQuantity = favoriteQuantity;
        }

        public int getShowQuantity() {
            return showQuantity;
        }

        public void setShowQuantity(int showQuantity) {
            this.showQuantity = showQuantity;
        }
    }
}
