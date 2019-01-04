package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class BenefitType {


    /**
     * cityName : string
     * district : [{"districtName":"string"}]
     */

    private String cityName;
    private List<DistrictBean> district;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<DistrictBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<DistrictBean> district) {
        this.district = district;
    }

    public static class DistrictBean {
        /**
         * districtName : string
         */

        private String districtName;

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }
    }
}
