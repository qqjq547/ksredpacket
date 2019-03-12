package com.guochuang.mimedia.mvp.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.guochuang.mimedia.tools.Constant;

public class Message implements MultiItemEntity {


    /**
     * avatar : string
     * commentId : 0
     * content : string
     * createDate : 2018-12-24T03:57:14.022Z
     * createUser : string
     * generatorUuid : string
     * id : 0
     * isReply : 0
     * nickName : string
     * parentCommentId : 0
     * sequence : 0
     * sourceContent : string
     * sourcePicture : string
     * sourceUuid : string
     * tenantCode : string
     * title : string
     * type : string
     * updateDate : 2018-12-24T03:57:14.022Z
     * updateUser : string
     * userAccountUuid : string
     */

    private String avatar;
    private long commentId;
    private String content;
    private String createDate;
    private String createUser;
    private String generatorUuid;
    private int id;
    private int isReply;
    private String nickName;
    private long parentCommentId;
    private int sequence;
    private String sourceContent;
    private String sourcePicture;
    private String sourceUuid;
    private String tenantCode;
    private String title;
    private String type;
    private String updateDate;
    private String updateUser;
    private String userAccountUuid;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

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

    public String getGeneratorUuid() {
        return generatorUuid;
    }

    public void setGeneratorUuid(String generatorUuid) {
        this.generatorUuid = generatorUuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getSourceContent() {
        return sourceContent;
    }

    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public String getSourcePicture() {
        return sourcePicture;
    }

    public void setSourcePicture(String sourcePicture) {
        this.sourcePicture = sourcePicture;
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
    @Override
    public int getItemType() {
        int itemType=0;
        if (TextUtils.isEmpty(type)){
            return itemType;
        }
        switch (type){
            case Constant.MSG_TYPE_NOTICE:
                itemType=0;
                break;
            case Constant.MSG_TYPE_COMMENT:
            case Constant.MSG_TYPE_REDPACKET:
                itemType=1;
                break;
            case Constant.MSG_TYPE_SNATCHACTIVITY:
                itemType=2;
                break;

        }
        return itemType;
    }
}
