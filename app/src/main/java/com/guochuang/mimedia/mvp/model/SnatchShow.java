package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class SnatchShow {

    /**
     * content : string
     * imgs : ["string"]
     * isAdd : 0
     * isWin : 0
     * luckNum : string
     * payNum : 0
     * productUuid : string
     * snatchId : 0
     * snatchImg : string
     * snatchName : string
     * snatchNums : string
     * snatchPeriods : string
     * userAvatar : string
     * userNickName : string
     */

    private String content;
    private int isAdd;
    private int isWin;
    private String luckNum;
    private int payNum;
    private String productUuid;
    private long snatchId;
    private String snatchImg;
    private String snatchName;
    private String snatchNums;
    private String snatchPeriods;
    private String userAvatar;
    private String userNickName;
    private List<String> imgs;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }

    public int getIsWin() {
        return isWin;
    }

    public void setIsWin(int isWin) {
        this.isWin = isWin;
    }

    public String getLuckNum() {
        return luckNum;
    }

    public void setLuckNum(String luckNum) {
        this.luckNum = luckNum;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(String productUuid) {
        this.productUuid = productUuid;
    }

    public long getSnatchId() {
        return snatchId;
    }

    public void setSnatchId(long snatchId) {
        this.snatchId = snatchId;
    }

    public String getSnatchImg() {
        return snatchImg;
    }

    public void setSnatchImg(String snatchImg) {
        this.snatchImg = snatchImg;
    }

    public String getSnatchName() {
        return snatchName;
    }

    public void setSnatchName(String snatchName) {
        this.snatchName = snatchName;
    }

    public String getSnatchNums() {
        return snatchNums;
    }

    public void setSnatchNums(String snatchNums) {
        this.snatchNums = snatchNums;
    }

    public String getSnatchPeriods() {
        return snatchPeriods;
    }

    public void setSnatchPeriods(String snatchPeriods) {
        this.snatchPeriods = snatchPeriods;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
