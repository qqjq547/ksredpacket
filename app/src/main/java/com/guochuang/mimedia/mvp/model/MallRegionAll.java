package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class MallRegionAll {

    /**
     * cityList : [{"areaList":[{"code":"string","id":0,"level":0,"name":"string","parentId":0}],"code":"string","id":0,"level":0,"name":"string","parentId":0}]
     * code : string
     * id : 0
     * level : 0
     * name : string
     * parentId : 0
     */

    private String code;
    private int id;
    private int level;
    private String name;
    private int parentId;
    private List<CityListBean> cityList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * areaList : [{"code":"string","id":0,"level":0,"name":"string","parentId":0}]
         * code : string
         * id : 0
         * level : 0
         * name : string
         * parentId : 0
         */

        private String code;
        private int id;
        private int level;
        private String name;
        private int parentId;
        private List<AreaListBean> areaList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public List<AreaListBean> getAreaList() {
            return areaList;
        }

        public void setAreaList(List<AreaListBean> areaList) {
            this.areaList = areaList;
        }

        public static class AreaListBean {
            /**
             * code : string
             * id : 0
             * level : 0
             * name : string
             * parentId : 0
             */

            private String code;
            private int id;
            private int level;
            private String name;
            private int parentId;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }
        }
    }
}
