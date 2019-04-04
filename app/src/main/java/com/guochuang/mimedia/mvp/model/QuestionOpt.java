package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class QuestionOpt {

    /**
     * addReqDtoList : [{"optionsList":[{"isAnswer":0,"optionName":"string","optionValue":"string","sequence":0}],"sequence":0,"surveyId":0,"title":"string","type":0}]
     * areaType : 0
     * channelCode : string
     * content : string
     * coverUrl : string
     * isPublicPassword : 0
     * isSaveTemplate : 0
     * jumpUrl : string
     * kilometer : 0
     * microblog : string
     * money : 0
     * password : string
     * payType : 0
     * picture : string
     * quantity : 0
     * redPacketLatitude : 0
     * redPacketLongitude : 0
     * redPacketType : string
     * safetyCode : string
     * surveyType : 0
     * title : string
     * url : string
     * urlName : string
     * userLatitude : 0
     * userLongitude : 0
     * videoUrl : string
     * wechat : string
     */

    private int areaType;
    private String channelCode;
    private String content;
    private String coverUrl;
    private int isPublicPassword;
    private int isSaveTemplate;
    private String jumpUrl;
    private int kilometer;
    private String microblog;
    private int money;
    private String password;
    private int payType;
    private String picture;
    private int quantity;
    private int redPacketLatitude;
    private int redPacketLongitude;
    private String redPacketType;
    private String safetyCode;
    private int surveyType;
    private String title;
    private String url;
    private String urlName;
    private double userLatitude;
    private double userLongitude;
    private String videoUrl;
    private String wechat;
    private List<AddReqDtoListBean> addReqDtoList;

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getIsPublicPassword() {
        return isPublicPassword;
    }

    public void setIsPublicPassword(int isPublicPassword) {
        this.isPublicPassword = isPublicPassword;
    }

    public int getIsSaveTemplate() {
        return isSaveTemplate;
    }

    public void setIsSaveTemplate(int isSaveTemplate) {
        this.isSaveTemplate = isSaveTemplate;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getKilometer() {
        return kilometer;
    }

    public void setKilometer(int kilometer) {
        this.kilometer = kilometer;
    }

    public String getMicroblog() {
        return microblog;
    }

    public void setMicroblog(String microblog) {
        this.microblog = microblog;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedPacketLatitude() {
        return redPacketLatitude;
    }

    public void setRedPacketLatitude(int redPacketLatitude) {
        this.redPacketLatitude = redPacketLatitude;
    }

    public int getRedPacketLongitude() {
        return redPacketLongitude;
    }

    public void setRedPacketLongitude(int redPacketLongitude) {
        this.redPacketLongitude = redPacketLongitude;
    }

    public String getRedPacketType() {
        return redPacketType;
    }

    public void setRedPacketType(String redPacketType) {
        this.redPacketType = redPacketType;
    }

    public String getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode;
    }

    public int getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(int surveyType) {
        this.surveyType = surveyType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public List<AddReqDtoListBean> getAddReqDtoList() {
        return addReqDtoList;
    }

    public void setAddReqDtoList(List<AddReqDtoListBean> addReqDtoList) {
        this.addReqDtoList = addReqDtoList;
    }

    public static class AddReqDtoListBean {
        /**
         * optionsList : [{"isAnswer":0,"optionName":"string","optionValue":"string","sequence":0}]
         * sequence : 0
         * surveyId : 0
         * title : string
         * type : 0
         */

        private int sequence;
        private int surveyId;
        private String title;
        private int type;
        private List<OptionsListBean> optionsList;

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(int surveyId) {
            this.surveyId = surveyId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<OptionsListBean> getOptionsList() {
            return optionsList;
        }

        public void setOptionsList(List<OptionsListBean> optionsList) {
            this.optionsList = optionsList;
        }

        public static class OptionsListBean {
            /**
             * isAnswer : 0
             * optionName : string
             * optionValue : string
             * sequence : 0
             */

            private int isAnswer;
            private String optionName;
            private String optionValue;
            private int sequence;

            public int getIsAnswer() {
                return isAnswer;
            }

            public void setIsAnswer(int isAnswer) {
                this.isAnswer = isAnswer;
            }

            public String getOptionName() {
                return optionName;
            }

            public void setOptionName(String optionName) {
                this.optionName = optionName;
            }

            public String getOptionValue() {
                return optionValue;
            }

            public void setOptionValue(String optionValue) {
                this.optionValue = optionValue;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
        }
    }
}
