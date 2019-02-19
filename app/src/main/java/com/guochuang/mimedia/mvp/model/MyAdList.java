package com.guochuang.mimedia.mvp.model;

public class MyAdList {

    /**
     * endDate : string
     * isEdit : 0
     * lat : string
     * lng : string
     * nestInfoId : 0
     * nestLocationId : 0
     * nestStatisticsResDto : {"clickQuantity":0,"favoriteQuantity":0,"showQuantity":0}
     * nestTimeInfoId : 0
     * startDate : string
     * status : string
     * totalPrice : string
     * unitPrice : string
     */

    private String endDate;
    private int isEdit;
    private String lat;
    private String lng;
    private int nestInfoId;
    private int nestLocationId;
    private NestStatisticsResDtoBean nestStatisticsResDto;
    private int nestTimeInfoId;
    private String startDate;
    private String status;
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

    public int getNestInfoId() {
        return nestInfoId;
    }

    public void setNestInfoId(int nestInfoId) {
        this.nestInfoId = nestInfoId;
    }

    public int getNestLocationId() {
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

    public int getNestTimeInfoId() {
        return nestTimeInfoId;
    }

    public void setNestTimeInfoId(int nestTimeInfoId) {
        this.nestTimeInfoId = nestTimeInfoId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
