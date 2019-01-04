package com.guochuang.mimedia.mvp.model;

public class UploadFile {

    /**
     * originalName : jike_1809867895117874_pic.jpeg
     * newName : 9ca81699f91c42bfaaf1cfac38974d4a.jpeg
     * size : 105495
     * businessCode : 0093da1c08f6406599d5f0a28b7efb15
     * businessType : "redpacket"
     * url : http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/default/9ca81699f91c42bfaaf1cfac38974d4a.jpeg
     */

    private String originalName;
    private String newName;
    private int size;
    private String businessCode;
    private String businessType;
    private String url;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
