package com.guochuang.mimedia.mvp.model;

public class Category {

    /**
     * tenantCode : null
     * id : 2
     * createUser : null
     * createDate : 2018-11-09 17:28:41
     * updateUser : null
     * updateDate : null
     * parentId : 0
     * systemCode : WWW
     * name : 审核
     * code : 0001
     * picture : 1053
     * title : 审核新闻
     * keyword : 审核
     * description : 新闻好看不
     * type : 审核
     * sequence : 9
     * isEnabled : 1
     */

    private Object tenantCode;
    private int id;
    private String createDate;
    private int parentId;
    private String systemCode;
    private String name;
    private String code;
    private String picture;
    private String title;
    private String keyword;
    private String description;
    private String type;
    private int sequence;
    private int isEnabled;

    public Object getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(Object tenantCode) {
        this.tenantCode = tenantCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }
}
