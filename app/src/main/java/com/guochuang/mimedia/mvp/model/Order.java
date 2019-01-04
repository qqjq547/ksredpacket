package com.guochuang.mimedia.mvp.model;

public class Order {

    private String orderNumber;
    private String vendorResponse;
    private String resultCode;
    private String redPacketUuid;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getVendorResponse() {
        return vendorResponse;
    }

    public void setVendorResponse(String vendorResponse) {
        this.vendorResponse = vendorResponse;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }
}
