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
     *
     * [{"id":195,"parentId":0,"name":"全部","description":"全部","type":"qc_type","code":"100","children":null},{"id":186,"parentId":0,"name":"转换","description":"KSB明细子类","type":"qc_type","code":"101","children":null},{"id":187,"parentId":0,"name":"转赠","description":"KSB明细子类","type":"qc_type","code":"102","children":null},{"id":188,"parentId":0,"name":"发红包分润","description":"发红包分润","type":"qc_type","code":"104","children":null},{"id":189,"parentId":0,"name":"抢红包分润","description":"抢红包分润","type":"qc_type","code":"105","children":null},{"id":190,"parentId":0,"name":"抢红包","description":"抢红包","type":"qc_type","code":"106","children":null},{"id":191,"parentId":0,"name":"看资讯","description":"看资讯","type":"qc_type","code":"107","children":null},{"id":192,"parentId":0,"name":"城主分润","description":"城主分润","type":"qc_type","code":"109","children":null},{"id":193,"parentId":0,"name":"服务奖","description":"服务奖","type":"qc_type","code":"111","children":null},{"id":198,"parentId":0,"name":"团队奖","description":"团队奖","type":"qc_type","code":"122","children":null},{"id":194,"parentId":0,"name":"分享奖励","description":"分享奖励","type":"qc_type","code":"112","children":null},{"id":196,"parentId":0,"name":"蜂窝分润","description":"蜂窝分润","type":"qc_type","code":"118","children":null},{"id":197,"parentId":0,"name":"蜂窝收益","description":"蜂窝收益","type":"qc_type","code":"120","children":null},{"id":201,"parentId":0,"name":"蜂巢广告","description":"蜂巢广告","type":"qc_type","code":"125","children":null},{"id":200,"parentId":0,"name":"淘区块","description":"淘区块","type":"qc_type","code":"124","children":null},{"id":202,"parentId":0,"name":"收款码收款","description":"收款码收款","type":"qc_type","code":"129","children":null},{"id":203,"parentId":0,"name":"收款码付款","description":"收款码付款","type":"qc_type","code":"130","children":null},{"id":199,"parentId":0,"name":"支出","description":"支出","type":"qc_type","code":"123","children":null}]
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
