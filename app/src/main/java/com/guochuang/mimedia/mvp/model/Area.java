package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class Area {

    /**
     * id : 10
     * name : 重庆市
     * code : 9
     * children : [{"id":11,"name":"江培区","code":"11"},{"id":12,"name":"双流区","code":"12"},{"id":13,"name":"金牛区","code":"13"}]
     */

    private int id;
    private String name;
    private String code;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 11
         * name : 江培区
         * code : 11
         */

        private int id;
        private String name;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
