package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class MyRegion {

    /**
     * regionStatisticsDto : {"regionNumber":1,"totalIncome":"0","redPacketIncome":"0","compensate":"0"}
     * explain : 1、城主可以获得竞购差额30%的补偿
     2、城主可以使用城主专用公告板,发布个性公告
     3、城主可以获得区域内所有用户抢红包金额5%的分润,每天结算
     * myRegionListDto : [{"whereRegion":"深圳市宝安区","regionId":3,"biddingPrice":"39000","nickName":"HereAfter","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoBricHhrfR2vZQvjSX6KDaPD6HNmbjnxkpHJrVhwZm5FvPHTzIGhnWKia4Kq5ydMxicPIxffVADFRJQ/132","profit":"0","content":null,"picture":null,"show":true,"auction":true}]
     */

    private RegionStatisticsDtoBean regionStatisticsDto;
    private String explain;
    private List<MyRegionListDtoBean> myRegionListDto;

    public RegionStatisticsDtoBean getRegionStatisticsDto() {
        return regionStatisticsDto;
    }

    public void setRegionStatisticsDto(RegionStatisticsDtoBean regionStatisticsDto) {
        this.regionStatisticsDto = regionStatisticsDto;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<MyRegionListDtoBean> getMyRegionListDto() {
        return myRegionListDto;
    }

    public void setMyRegionListDto(List<MyRegionListDtoBean> myRegionListDto) {
        this.myRegionListDto = myRegionListDto;
    }

    public static class RegionStatisticsDtoBean {
        /**
         * regionNumber : 1
         * totalIncome : 0
         * redPacketIncome : 0
         * compensate : 0
         */

        private int regionNumber;
        private String totalIncome;
        private String redPacketIncome;
        private String compensate;

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

        public String getRedPacketIncome() {
            return redPacketIncome;
        }

        public void setRedPacketIncome(String redPacketIncome) {
            this.redPacketIncome = redPacketIncome;
        }

        public String getCompensate() {
            return compensate;
        }

        public void setCompensate(String compensate) {
            this.compensate = compensate;
        }
    }

    public static class MyRegionListDtoBean {
        /**
         * whereRegion : 深圳市宝安区
         * regionId : 3
         * biddingPrice : 39000
         * nickName : HereAfter
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoBricHhrfR2vZQvjSX6KDaPD6HNmbjnxkpHJrVhwZm5FvPHTzIGhnWKia4Kq5ydMxicPIxffVADFRJQ/132
         * profit : 0
         * content : null
         * picture : null
         * show : true
         * auction : true
         */

        private String whereRegion;
        private long regionId;
        private String biddingPrice;
        private String nickName;
        private String avatar;
        private String profit;
        private String content;
        private List<String> picture;
        private boolean show;
        private boolean auction;

        public String getWhereRegion() {
            return whereRegion;
        }

        public void setWhereRegion(String whereRegion) {
            this.whereRegion = whereRegion;
        }

        public long getRegionId() {
            return regionId;
        }

        public void setRegionId(long regionId) {
            this.regionId = regionId;
        }

        public String getBiddingPrice() {
            return biddingPrice;
        }

        public void setBiddingPrice(String biddingPrice) {
            this.biddingPrice = biddingPrice;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public boolean isAuction() {
            return auction;
        }

        public void setAuction(boolean auction) {
            this.auction = auction;
        }
    }
}
