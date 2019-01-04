package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class GeoCode {


    /**
     * addressComponent : {"adcode":"string","city":"string","cityLevel":"string","country":"string","countryCode":"string","countryCodeIso":"string","countryCodeIso2":"string","direction":"string","distance":"string","district":"string","province":"string","street":"string","streetNumber":"string","town":"string"}
     * business : string
     * cityCode : 0
     * formattedAddress : string
     * location : {"lat":0,"lng":0}
     * poiRegions : [{"directionDesc":"string","name":"string","tag":"string","uid":"string"}]
     * pois : [{"addr":"string","cp":"string","direction":"string","distance":"string","name":"string","parentPoi":{},"poiType":"string","point":{"x":0,"y":0},"tag":"string","tel":"string","uid":"string","zip":"string"}]
     * sematicDescription : string
     */

    private AddressComponentBean addressComponent;
    private String business;
    private int cityCode;
    private String formattedAddress;
    private LocationBean location;
    private String sematicDescription;
    private List<PoiRegionsBean> poiRegions;
    private List<PoisBean> pois;


    public AddressComponentBean getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponentBean addressComponent) {
        this.addressComponent = addressComponent;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public String getSematicDescription() {
        return sematicDescription;
    }

    public void setSematicDescription(String sematicDescription) {
        this.sematicDescription = sematicDescription;
    }

    public List<PoiRegionsBean> getPoiRegions() {
        return poiRegions;
    }

    public void setPoiRegions(List<PoiRegionsBean> poiRegions) {
        this.poiRegions = poiRegions;
    }

    public List<PoisBean> getPois() {
        return pois;
    }

    public void setPois(List<PoisBean> pois) {
        this.pois = pois;
    }

    public static class AddressComponentBean {
        /**
         * adcode : string
         * city : string
         * cityLevel : string
         * country : string
         * countryCode : string
         * countryCodeIso : string
         * countryCodeIso2 : string
         * direction : string
         * distance : string
         * district : string
         * province : string
         * street : string
         * streetNumber : string
         * town : string
         */

        private String adcode;
        private String city;
        private String cityLevel;
        private String country;
        private String countryCode;
        private String countryCodeIso;
        private String countryCodeIso2;
        private String direction;
        private String distance;
        private String district;
        private String province;
        private String street;
        private String streetNumber;
        private String town;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityLevel() {
            return cityLevel;
        }

        public void setCityLevel(String cityLevel) {
            this.cityLevel = cityLevel;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCodeIso() {
            return countryCodeIso;
        }

        public void setCountryCodeIso(String countryCodeIso) {
            this.countryCodeIso = countryCodeIso;
        }

        public String getCountryCodeIso2() {
            return countryCodeIso2;
        }

        public void setCountryCodeIso2(String countryCodeIso2) {
            this.countryCodeIso2 = countryCodeIso2;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }
    }

    public static class LocationBean {
        /**
         * lat : 0
         * lng : 0
         */

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class PoiRegionsBean {
        /**
         * directionDesc : string
         * name : string
         * tag : string
         * uid : string
         */

        private String directionDesc;
        private String name;
        private String tag;
        private String uid;

        public String getDirectionDesc() {
            return directionDesc;
        }

        public void setDirectionDesc(String directionDesc) {
            this.directionDesc = directionDesc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
    public static class PoisBean{

        /**
         * addr : string
         * cp : string
         * direction : string
         * distance : string
         * name : string
         * parentPoi : {}
         * poiType : string
         * point : {"x":0,"y":0}
         * tag : string
         * tel : string
         * uid : string
         * zip : string
         */

        private String addr;
        private String cp;
        private String direction;
        private String distance;
        private String name;
        private ParentPoiBean parentPoi;
        private String poiType;
        private PointBean point;
        private String tag;
        private String tel;
        private String uid;
        private String zip;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getCp() {
            return cp;
        }

        public void setCp(String cp) {
            this.cp = cp;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ParentPoiBean getParentPoi() {
            return parentPoi;
        }

        public void setParentPoi(ParentPoiBean parentPoi) {
            this.parentPoi = parentPoi;
        }

        public String getPoiType() {
            return poiType;
        }

        public void setPoiType(String poiType) {
            this.poiType = poiType;
        }

        public PointBean getPoint() {
            return point;
        }

        public void setPoint(PointBean point) {
            this.point = point;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public static class ParentPoiBean {
        }

        public static class PointBean {
            /**
             * x : 0
             * y : 0
             */

            private double x;
            private double y;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
    }
}
