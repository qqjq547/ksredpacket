package com.guochuang.mimedia.mvp.model;

public class Notice {

    /**
     * id : null
     * description : 最新首页轮播测试测试测试
     * type : notice
     * beginDate : 2019-08-06 18:37:58
     * endDate : 2019-08-06 19:07:58
     * isEnabled : null
     * sourceId : 1284
     */

    private long id;
    private String description;
    private String type;
    private String beginDate;
    private String endDate;
    private long sourceId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }
}
