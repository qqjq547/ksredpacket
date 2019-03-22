package com.guochuang.mimedia.mvp.model;

import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

public class ShareBg {
    int image;

    public ShareBg(int image, int zoomImage) {
        this.image = image;
        this.zoomImage = zoomImage;
    }

    public int getZoomImage() {
        return zoomImage;
    }

    public void setZoomImage(int zoomImage) {
        this.zoomImage = zoomImage;
    }

    int zoomImage;
    String des = Constant.INVITE_FRIENDS_GIVE_GIFTS;



    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
