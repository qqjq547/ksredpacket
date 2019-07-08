package com.guochuang.mimedia.mvp.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class RedPacketReply implements MultiItemEntity {
    public static final int REPLY=0;
    public static final int OTHER=1;
    long id;
    private String avatar;
    private long commentTimeStamp;
    private String content;
    private String ip;
    private String nickName;
    private long redPacketId;
    private String redPacketUuid;
    private String userAccountUuid;
    private int isCanReply;
    private List<String> commentList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getItemType() {
        return id>0?REPLY:OTHER;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(long commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public int getIsCanReply() {
        return isCanReply;
    }

    public void setIsCanReply(int isCanReply) {
        this.isCanReply = isCanReply;
    }

    public List<String> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<String> commentList) {
        this.commentList = commentList;
    }
}
