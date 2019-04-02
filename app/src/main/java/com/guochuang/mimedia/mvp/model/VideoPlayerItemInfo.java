package com.guochuang.mimedia.mvp.model;

/**
 */
public class VideoPlayerItemInfo {
    public int id;
    public String url;
    public int width;
    public int height;
    //...

    public VideoPlayerItemInfo(int id, String url, int width, int height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height =height;
    }

}
