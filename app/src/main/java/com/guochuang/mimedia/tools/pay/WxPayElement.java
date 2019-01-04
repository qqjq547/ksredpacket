package com.guochuang.mimedia.tools.pay;

public class WxPayElement {

    /**
     * sign : 87E7E68B98E65F69C08ED3858E3666A6
     * appid : wx63f201fbc38054a9
     * partnerid : 1519656401
     * prepayid : wx30135959509444560685d66c2402709898
     * packageValue : Sign=WXPay
     * timestamp : 1543557599
     * noncestr : aZttz5i1EMHMDHXg
     */

    private String sign;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String packageValue;
    private String timestamp;
    private String noncestr;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }
}
