package com.guochuang.mimedia.mvp.model;

public class Reply {

    /**
     * articleId : 1
     * articleUuid : 82f3c2c6a0d447e4aa80fd187bf70e57
     * userAccountUuid : admin_uuid
     * content : testtest
     * ip : 192.168.0.105
     * avatar : https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=2558494360,3043968263&fm=85&s=9C878A5ECA0EFF3E1D3A5258030010F8
     * nickName : Test
     */

    private int articleId;
    private String articleUuid;
    private String userAccountUuid;
    private String content;
    private String ip;
    private String avatar;
    private String nickName;
    private long commentTimeStamp;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleUuid() {
        return articleUuid;
    }

    public void setArticleUuid(String articleUuid) {
        this.articleUuid = articleUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public long getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(long commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
    }
}
