package com.guochuang.mimedia.view.rain;/**
 * Created by lenovo on 2016/12/6.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.CommonUtil;

/**
 * 流星线精灵
 * create by yao.cui at 2016/12/6
 */
public class CircleSprite extends LineAnimSprite {

    int radius;
    public CircleSprite(Context context, int pWidth, int pHeight){
        super(context,pWidth,pHeight);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg_rain_circle);
        srcBmp = scaleBmp(bmp, CommonUtil.dip2px(context,140),true);
        point = newPosition(true,0,0);
        radius=CommonUtil.dip2px(context,20);
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        if (point[0] + srcBmp.getWidth() < 0){
            isOver = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        point = newPosition(true,0,0);
        time = 0;
    }
}
