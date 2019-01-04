package com.guochuang.mimedia.view.rain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.CommonUtil;

import java.util.Random;

/**
 * 红包雨精灵
 * create by yao.cui at 2016/12/1
 */
public class MeteorSprite extends LineAnimSprite {

    private Random mRandom = new Random();

    public MeteorSprite(Context context, int pWidth, int pHeihgt) {
        super(context,pWidth,pHeihgt);
        clickable = true;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg_rain_redpacket);
        width = CommonUtil.dip2px(context,40);
        srcBmp = scaleBmp(bmp,width,true);
        bmp.recycle();
        height = srcBmp.getHeight();
        point = newPosition(true,0,0);
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        //精灵滑出屏幕标记可完成，使其被回收
        if (point[0] + srcBmp.getWidth()< 0){
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
