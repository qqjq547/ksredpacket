package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class RedbagRecord {

    /**
     * avatar : string
     * browserNumber : 0
     * commentNumber : 0
     * content : string
     * drawPerson : 0
     * favoriteNumber : 0
     * nickName : string
     * pictureList : [{"content":"string","createDate":"2018-11-26T06:11:28.223Z","createUser":"string","id":0,"jumpUrl":"string","picture":"string","redPacketId":0,"redPacketUuid":"string","tenantCode":"string","title":"string","updateDate":"2018-11-26T06:11:28.223Z","updateUser":"string"}]
     * praiseNumber : 0
     * redPacketUuid : string
     * totalCount : 0
     * totalMoney : 0
     * userAccountUuid : string
     */

    private String avatar;
    private int browserNumber;
    private int commentNumber;
    private String content;
    private int drawPerson;
    private int favoriteNumber;
    private String nickName;
    private int praiseNumber;
    private String redPacketUuid;
    private int totalCount;
    private double totalMoney;
    private String userAccountUuid;
    private String redPacketType;
    private String password;
    private int quantity;
    private double money;
    private String surveyId;
    private List<PictureListBean> pictureList;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBrowserNumber() {
        return browserNumber;
    }

    public void setBrowserNumber(int browserNumber) {
        this.browserNumber = browserNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDrawPerson() {
        return drawPerson;
    }

    public void setDrawPerson(int drawPerson) {
        this.drawPerson = drawPerson;
    }

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getRedPacketType() {
        return redPacketType;
    }

    public void setRedPacketType(String redPacketType) {
        this.redPacketType = redPacketType;
    }

    public List<PictureListBean> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureListBean> pictureList) {
        this.pictureList = pictureList;
    }

    public static class PictureListBean {
        /**
         * content : string
         * createDate : 2018-11-26T06:11:28.223Z
         * createUser : string
         * id : 0
         * jumpUrl : string
         * picture : string
         * redPacketId : 0
         * redPacketUuid : string
         * tenantCode : string
         * title : string
         * updateDate : 2018-11-26T06:11:28.223Z
         * updateUser : string
         */

        private String content;
        private String createDate;
        private String createUser;
        private int id;
        private String jumpUrl;
        private String picture;
        private int redPacketId;
        private String redPacketUuid;
        private String tenantCode;
        private String title;
        private String updateDate;
        private String updateUser;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(int redPacketId) {
            this.redPacketId = redPacketId;
        }

        public String getRedPacketUuid() {
            return redPacketUuid;
        }

        public void setRedPacketUuid(String redPacketUuid) {
            this.redPacketUuid = redPacketUuid;
        }

        public String getTenantCode() {
            return tenantCode;
        }

        public void setTenantCode(String tenantCode) {
            this.tenantCode = tenantCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }
}
