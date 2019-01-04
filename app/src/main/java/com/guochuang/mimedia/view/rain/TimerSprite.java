package com.guochuang.mimedia.view.rain;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.CommonUtil;

/**
 * create by yao.cui at 2016/12/8
 */
public class TimerSprite extends BaseSprite {
    private Paint mTxtPaint;
    private Rect mScoreBounds = new Rect();
    private Rect mTargetRect;
    private Rect mOrgRect;
    private int mTime;//倒计时还剩多少秒

    public TimerSprite(Context context, int pWidth, int pHeight){
        super(context,pWidth,pHeight);
        srcBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_rain_countdown);

        int targetWidth =(int) (pWidth/4f);
        int targetHeight = targetWidth* srcBmp.getHeight()/srcBmp.getWidth();
        mTargetRect = new Rect(30, CommonUtil.dip2px(context,10)
                ,targetWidth+30,CommonUtil.dip2px(context,10)+targetHeight);
        mOrgRect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());

        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(Color.parseColor("#ff8c20"));
        mTxtPaint.setTextSize(CommonUtil.dip2px(context,15));
        mTxtPaint.getTextBounds("000",0,3,mScoreBounds);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(srcBmp,mOrgRect,mTargetRect,new Paint());
        canvas.drawText(mTime +" ",mTargetRect.left+mTargetRect.width()/3*2-mScoreBounds.width()/2,
                mTargetRect.centerY()+mScoreBounds.height()/2, mTxtPaint);
    }

    @Override
    public void recycle() {
        super.recycle();
    }

    @Override
    public void stop() {
        isOver = true;
    }

    public void updateTime(int time){
        this.mTime = time;
    }
}
