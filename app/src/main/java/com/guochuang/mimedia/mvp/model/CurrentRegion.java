package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class CurrentRegion {

    /**
     * avatar : string
     * bidding : true
     * biddingHall : [{"avatar":"string","grats":"string","nickName":"string","price":"string","prosit":"string"}]
     * biddingPrice : string
     * biddingTime : string
     * content : string
     * error : string
     * nickName : string
     * picture : ["string"]
     * regionId : 0
     * regionStatisticsDto : {"compensate":"string","redPacketIncome":"string","regionNumber":0,"totalIncome":"string"}
     * url : string
     * urlName : string
     * whereRegion : string
     */

    private String avatar;
    private boolean bidding;
    private String biddingPrice;
    private String biddingTime;
    private String content;
    private String error;
    private String nickName;
    private long regionId;
    private RegionStatisticsDtoBean regionStatisticsDto;
    private String url;
    private String urlName;
    private String whereRegion;
    private boolean dereliction;
    private List<BiddingHallBean> biddingHall;
    private List<String> picture;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isBidding() {
        return bidding;
    }

    public void setBidding(boolean bidding) {
        this.bidding = bidding;
    }

    public String getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(String biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public String getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(String biddingTime) {
        this.biddingTime = biddingTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public RegionStatisticsDtoBean getRegionStatisticsDto() {
        return regionStatisticsDto;
    }

    public void setRegionStatisticsDto(RegionStatisticsDtoBean regionStatisticsDto) {
        this.regionStatisticsDto = regionStatisticsDto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getWhereRegion() {
        return whereRegion;
    }

    public void setWhereRegion(String whereRegion) {
        this.whereRegion = whereRegion;
    }

    public List<BiddingHallBean> getBiddingHall() {
        return biddingHall;
    }

    public void setBiddingHall(List<BiddingHallBean> biddingHall) {
        this.biddingHall = biddingHall;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public boolean isDereliction() {
        return dereliction;
    }

    public void setDereliction(boolean dereliction) {
        this.dereliction = dereliction;
    }

    public static class RegionStatisticsDtoBean {
        /**
         * compensate : string
         * redPacketIncome : string
         * regionNumber : 0
         * totalIncome : string
         */

        private String compensate;
        private String redPacketIncome;
        private int regionNumber;
        private String totalIncome;

        public String getCompensate() {
            return compensate;
        }

        public void setCompensate(String compensate) {
            this.compensate = compensate;
        }

        public String getRedPacketIncome() {
            return redPacketIncome;
        }

        public void setRedPacketIncome(String redPacketIncome) {
            this.redPacketIncome = redPacketIncome;
        }

        public int getRegionNumber() {
            return regionNumber;
        }

        public void setRegionNumber(int regionNumber) {
            this.regionNumber = regionNumber;
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }
    }

    public static class BiddingHallBean {
        /**
         * avatar : string
         * grats : string
         * nickName : string
         * price : string
         * prosit : string
         */

        private String avatar;
        private String grats;
        private String nickName;
        private String price;
        private String prosit;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGrats() {
            return grats;
        }

        public void setGrats(String grats) {
            this.grats = grats;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProsit() {
            return prosit;
        }

        public void setProsit(String prosit) {
            this.prosit = prosit;
        }
    }
}
