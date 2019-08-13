package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class MallRegionInfo {

    /**
     * acronym : string
     * code : string
     * createDate : 2019-08-05T02:11:04.275Z
     * createUser : string
     * description : string
     * id : 0
     * initial : string
     * isEnabled : 0
     * isLock : 0
     * level : 0
     * maxCoin : 0
     * maxPrice : 0
     * maxQc : 0
     * minCoin : 0
     * minPrice : 0
     * minQc : 0
     * name : string
     * num : 0
     * parentId : 0
     * pinyin : string
     * sequence : 0
     * tenantCode : string
     * updateDate : 2019-08-05T02:11:04.275Z
     * updateUser : string
     */

    private String acronym;
    private String code;
    private String createDate;
    private String createUser;
    private String description;
    private long id;
    private String initial;
    private int isEnabled;
    private int isLock;
    private int level;
    private int maxCoin;
    private int maxPrice;
    private int maxQc;
    private int minCoin;
    private int minPrice;
    private int minQc;
    private String name;
    private int num;
    private int parentId;
    private String pinyin;
    private int sequence;
    private String tenantCode;
    private String updateDate;
    private String updateUser;
    private int stepPrice;
    private List<RegionNodeListBean> regionNodeList;

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxCoin() {
        return maxCoin;
    }

    public void setMaxCoin(int maxCoin) {
        this.maxCoin = maxCoin;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMaxQc() {
        return maxQc;
    }

    public void setMaxQc(int maxQc) {
        this.maxQc = maxQc;
    }

    public int getMinCoin() {
        return minCoin;
    }

    public void setMinCoin(int minCoin) {
        this.minCoin = minCoin;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMinQc() {
        return minQc;
    }

    public void setMinQc(int minQc) {
        this.minQc = minQc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public int getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
    }

    public List<RegionNodeListBean> getRegionNodeList() {
        return regionNodeList;
    }

    public void setRegionNodeList(List<RegionNodeListBean> regionNodeList) {
        this.regionNodeList = regionNodeList;
    }

    public static class RegionNodeListBean {
        /**
         * nodeNumber : 1
         * isBuy : 0
         */

        private int nodeNumber;
        private int isBuy;

        public int getNodeNumber() {
            return nodeNumber;
        }

        public void setNodeNumber(int nodeNumber) {
            this.nodeNumber = nodeNumber;
        }

        public int getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(int isBuy) {
            this.isBuy = isBuy;
        }
    }
}
