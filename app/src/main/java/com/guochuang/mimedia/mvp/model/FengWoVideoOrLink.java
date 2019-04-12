package com.guochuang.mimedia.mvp.model;

public class FengWoVideoOrLink {
    /**
     *    "path": "http://sz-gcyh.oss-cn-shenzhen.aliyuncs.com/video/1555052292447.mp4",
     *             "type": 1
     *
     *             1  视频   2 链接
     */


   private String path;
   private int type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
