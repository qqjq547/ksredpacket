package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class AdInfo {

    /**
     * defaultAppId : 1108034968
     * vendorAdList : [{"appId":"1108034968","vendorType":"tencent","vendorAdList":[{"appId":"1108034968","locationId":"4080943860199119","type":"1001"},{"appId":"1108034968","locationId":"5030540729471947","type":"1000"},{"appId":"1108034968","locationId":"4050447759674968","type":"1002"}]},{"appId":"c8a9f32f","vendorType":"baidu","vendorAdList":[{"appId":"c8a9f32f","locationId":"6003593","type":"1001"},{"appId":"c8a9f32f","locationId":"5998761","type":"1000"},{"appId":"c8a9f32f","locationId":"5998981","type":"1002"}]}]
     * systemAdList : [{"name":"升级代理商的好处","picture":"/asa/asg/sdfgsd/sdfg/5465sd.png","description":"升级代理商的好处","jumpUrl":"https://www.baidu.com/","height":100,"width":100}]
     */

    private String defaultAppId;
    private String defaultVendorType;
    private List<VendorAdListBeanX> vendorAdList;
    private List<SystemAdListBean> systemAdList;

    public String getDefaultVendorType() {
        return defaultVendorType;
    }

    public void setDefaultVendorType(String defaultVendorType) {
        this.defaultVendorType = defaultVendorType;
    }

    public String getDefaultAppId() {
        return defaultAppId;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }

    public List<VendorAdListBeanX> getVendorAdList() {
        return vendorAdList;
    }

    public void setVendorAdList(List<VendorAdListBeanX> vendorAdList) {
        this.vendorAdList = vendorAdList;
    }

    public List<SystemAdListBean> getSystemAdList() {
        return systemAdList;
    }

    public void setSystemAdList(List<SystemAdListBean> systemAdList) {
        this.systemAdList = systemAdList;
    }

    public static class VendorAdListBeanX {
        /**
         * appId : 1108034968
         * vendorType : tencent
         * vendorAdList : [{"appId":"1108034968","locationId":"4080943860199119","type":"1001"},{"appId":"1108034968","locationId":"5030540729471947","type":"1000"},{"appId":"1108034968","locationId":"4050447759674968","type":"1002"}]
         */

        private String appId;
        private String vendorType;
        private List<VendorAdListBean> vendorAdList;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getVendorType() {
            return vendorType;
        }

        public void setVendorType(String vendorType) {
            this.vendorType = vendorType;
        }

        public List<VendorAdListBean> getVendorAdList() {
            return vendorAdList;
        }

        public void setVendorAdList(List<VendorAdListBean> vendorAdList) {
            this.vendorAdList = vendorAdList;
        }

        public static class VendorAdListBean {
            /**
             * appId : 1108034968
             * locationId : 4080943860199119
             * type : 1001
             */

            private String appId;
            private String locationId;
            private String type;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getLocationId() {
                return locationId;
            }

            public void setLocationId(String locationId) {
                this.locationId = locationId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class SystemAdListBean {
        /**
         * name : 升级代理商的好处
         * picture : /asa/asg/sdfgsd/sdfg/5465sd.png
         * description : 升级代理商的好处
         * jumpUrl : https://www.baidu.com/
         * height : 100
         * width : 100
         */

        private String name;
        private String picture;
        private String description;
        private String jumpUrl;
        private int height;
        private int width;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
