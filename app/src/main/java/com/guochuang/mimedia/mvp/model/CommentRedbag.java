package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class CommentRedbag {

    /**
     * userAccountUuid : 6c94395b750645a593e013cd985317dc
     * redPacketUuid : b68435176b50423f86089aafc854da3c
     * avatar : test
     * nickName : test
     * redPacketContent : 155699999996666
     * commentContent : vvbhbbnbbbb
     * commentNumber : 0
     * praiseNumber : 0
     * favoriteNumber : 0
     * browserNumber : 0
     * pictureList : [{"tenantCode":"gcyh","id":45,"createUser":null,"createDate":"2018-11-26 14:19:13","updateUser":null,"updateDate":null,"redPacketId":18,"redPacketUuid":"b68435176b50423f86089aafc854da3c","title":null,"picture":"http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/default/a7a185748a164cda93ac546fca3c413b.jpg","content":"155699999996666","jumpUrl":null},{"tenantCode":"gcyh","id":46,"createUser":null,"createDate":"2018-11-26 14:19:13","updateUser":null,"updateDate":null,"redPacketId":18,"redPacketUuid":"b68435176b50423f86089aafc854da3c","title":null,"picture":"http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/default/ac08577e3b564fa8b1d13aac2c1c60d8.jpg","content":"155699999996666","jumpUrl":null}]
     */

    private String userAccountUuid;
    private String redPacketUuid;
    private String avatar;
    private String nickName;
    private String redPacketContent;
    private String commentContent;
    private int commentNumber;
    private int praiseNumber;
    private int favoriteNumber;
    private int browserNumber;
    private String  roleType;
    private String  redPacketType;
    private List<PictureListBean> pictureList;
    private long commentId;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRedPacketContent() {
        return redPacketContent;
    }

    public void setRedPacketContent(String redPacketContent) {
        this.redPacketContent = redPacketContent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public int getBrowserNumber() {
        return browserNumber;
    }

    public void setBrowserNumber(int browserNumber) {
        this.browserNumber = browserNumber;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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
         * tenantCode : gcyh
         * id : 45
         * createUser : null
         * createDate : 2018-11-26 14:19:13
         * updateUser : null
         * updateDate : null
         * redPacketId : 18
         * redPacketUuid : b68435176b50423f86089aafc854da3c
         * title : null
         * picture : http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/default/a7a185748a164cda93ac546fca3c413b.jpg
         * content : 155699999996666
         * jumpUrl : null
         */

        private String tenantCode;
        private int id;
        private Object createUser;
        private String createDate;
        private Object updateUser;
        private Object updateDate;
        private int redPacketId;
        private String redPacketUuid;
        private Object title;
        private String picture;
        private String content;
        private Object jumpUrl;

        public String getTenantCode() {
            return tenantCode;
        }

        public void setTenantCode(String tenantCode) {
            this.tenantCode = tenantCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(Object updateUser) {
            this.updateUser = updateUser;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
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

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(Object jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
