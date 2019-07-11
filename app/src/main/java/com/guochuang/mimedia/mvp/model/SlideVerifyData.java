package com.guochuang.mimedia.mvp.model;

public class SlideVerifyData {

    /**
     * channel : string
     * oriCopyImages : string
     * oriImages : string
     * slideImages : string
     * uniqueFlag : string
     */

    private String channel;
    private String oriCopyImages;
    private String oriImages;
    private String slideImages;
    private String uniqueFlag;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOriCopyImages() {
        return oriCopyImages;
    }

    public void setOriCopyImages(String oriCopyImages) {
        this.oriCopyImages = oriCopyImages;
    }

    public String getOriImages() {
        return oriImages;
    }

    public void setOriImages(String oriImages) {
        this.oriImages = oriImages;
    }

    public String getSlideImages() {
        return slideImages;
    }

    public void setSlideImages(String slideImages) {
        this.slideImages = slideImages;
    }

    public String getUniqueFlag() {
        return uniqueFlag;
    }

    public void setUniqueFlag(String uniqueFlag) {
        this.uniqueFlag = uniqueFlag;
    }
}
