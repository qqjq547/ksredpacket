package com.guochuang.mimedia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class CustomProgress extends View {
    private int defultcolor = Color.parseColor("#7db6fe");
    private int bottomcolor = Color.parseColor("#eeeeee");
    private int mProgress = 0;
    private int mMaxProgress;

    private int mPaintWidth = 14;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private Paint mPaint, mBottoPaint;


    public CustomProgress(Context context) {
        this(context, null);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = initPaint(defultcolor);
        mBottoPaint = initPaint(bottomcolor);
    }

    /**
     * 初始画笔
     */
    private Paint initPaint(int color) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(dp2px(mPaintWidth));//设置画笔宽度
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(color);
        return paint;
    }


    //放在 recycle 中测量不到大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(mWidth,mHeight );

    }


    /**
     * 绘制进度
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        //计算当前进度值


        canvas.drawLine(0, 0, mWidth, 0, mBottoPaint);

        if(mMaxProgress > 0) {
            canvas.drawLine(0, 0, mWidth / mMaxProgress * mProgress, 0, mPaint);
        }


    }


    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }


    public void setMaxProgress(int progress) {
        mMaxProgress = progress;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
    }


}
