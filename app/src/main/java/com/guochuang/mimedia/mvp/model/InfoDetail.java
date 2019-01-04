package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class InfoDetail {

    /**
     * articleId : 3
     * title : dddddd
     * author : guwm
     * content : null
     * picture : https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2895986097,3609514076&fm=173&app=25&f=JPEG?w=600&h=400&s=DBACB7475B8662D2062E5B6D0300E068
     * commentNumber : 2
     * praiseNumber : 2
     * initNumber : null
     * sourcePublishDate : null
     * isPraised : 1
     */

    private int articleId;
    private String title;
    private String author;
    private String content;
    private String picture;
    private int commentNumber;
    private int praiseNumber;
    private int initNumber;
    private String sourcePublishDate;
    private List<SystemAdListBean> systemAdList;
    private int isPraised;
    private int isFavorited;
    private int isBonusSecond;
    private int isBonus;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public int getInitNumber() {
        return initNumber;
    }

    public void setInitNumber(int initNumber) {
        this.initNumber = initNumber;
    }

    public String getSourcePublishDate() {
        return sourcePublishDate;
    }

    public void setSourcePublishDate(String sourcePublishDate) {
        this.sourcePublishDate = sourcePublishDate;
    }

    public List<SystemAdListBean> getSystemAdList() {
        return systemAdList;
    }

    public void setSystemAdList(List<SystemAdListBean> systemAdList) {
        this.systemAdList = systemAdList;
    }

    public int getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(int isPraised) {
        this.isPraised = isPraised;
    }

    public int getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(int isFavorited) {
        this.isFavorited = isFavorited;
    }

    public int getIsBonusSecond() {
        return isBonusSecond;
    }

    public void setIsBonusSecond(int isBonusSecond) {
        this.isBonusSecond = isBonusSecond;
    }

    public int getIsBonus() {
        return isBonus;
    }

    public void setIsBonus(int isBonus) {
        this.isBonus = isBonus;
    }

    public static class SystemAdListBean {

        /**
         * content : string
         * jumpUrl : string
         * picture : string
         * title : string
         */

        private String content;
        private String jumpUrl;
        private String picture;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
