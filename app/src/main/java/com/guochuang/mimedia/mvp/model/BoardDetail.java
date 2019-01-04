package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class BoardDetail {

    /**
     * content : string
     * picture : ["string"]
     * regionId : 0
     * url : string
     * urlName : string
     */

    private String content;
    private long regionId;
    private String url;
    private String urlName;
    private List<String> picture;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
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

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
