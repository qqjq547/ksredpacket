package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class DictionaryType {

    /**
     * id : 39
     * parentId : 0
     * name : 广告收入
     * description : ad
     * type : RegionStatisticsType
     * children : [{"id":46,"parentId":39,"name":"全部广告收入","description":null,"type":"RegionStatisticsType","children":null}]
     */

    private int id;
    private int parentId;
    private String name;
    private String description;
    private String type;
    private String code;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 46
         * parentId : 39
         * name : 全部广告收入
         * description : null
         * type : RegionStatisticsType
         * children : null
         */

        private int id;
        private int parentId;
        private String name;
        private String description;
        private String type;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
