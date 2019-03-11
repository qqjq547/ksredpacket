package com.guochuang.mimedia.mvp.model;

public class MyAd {

    /**
     * endDate : string
     * isEdit : 0
     * lat : string
     * lng : string
     * nestInfoId : 0
     * nestLocationId : 0
     * nestStatisticsResDto : {"clickQuantity":0,"favoriteQuantity":0,"showQuantity":0}
     * nestSuccessId : 0
     * startDate : string
     * status : string
     * totalPrice : string
     * unitPrice : string
     */

    private String endDate;
    private int isEdit;
    private String lat;
    private String lng;
    private long nestInfoId;
    private long nestLocationId;
    private NestStatisticsResDtoBean nestStatisticsResDto;
    private long nestSuccessId;
    private String startDate;
    private int status;
    private String totalPrice;
    private String unitPrice;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public long getNestInfoId() {
        return nestInfoId;
    }

    public void setNestInfoId(long nestInfoId) {
        this.nestInfoId = nestInfoId;
    }

    public long getNestLocationId() {
        return nestLocationId;
    }

    public void setNestLocationId(int nestLocationId) {
        this.nestLocationId = nestLocationId;
    }

    public NestStatisticsResDtoBean getNestStatisticsResDto() {
        return nestStatisticsResDto;
    }

    public void setNestStatisticsResDto(NestStatisticsResDtoBean nestStatisticsResDto) {
        this.nestStatisticsResDto = nestStatisticsResDto;
    }

    public long getNestSuccessId() {
        return nestSuccessId;
    }

    public void setNestSuccessId(long nestSuccessId) {
        this.nestSuccessId = nestSuccessId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
