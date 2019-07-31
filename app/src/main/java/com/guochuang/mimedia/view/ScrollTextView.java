package com.guochuang.mimedia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.LogUtil;


public class ScrollTextView extends TextView {
    private final float DEF_TEXT_SIZE = 25.0F;//The default text size
    private float mSpeed = 1.5F; //The default text scroll speed
    private boolean isScroll = true; //The default set as auto scroll
    private Context mContext;
    private Paint mPaint;
    private String mText;//This is to display the content
    private float mTextSize;//This is text size
    private int mTextColor; //This is text color

    private float mCoordinateX;//Draw the starting point of the X coordinate
    private float mCoordinateY;//Draw the starting point of the Y coordinate
    private float mTextWidth; //This is text width
    private int mViewWidth; //This is View width
    private onTextClickListener onTextClickListener;

    public interface onTextClickListener{
        void onTextClick(String text);
    }

    public void setOnTextClickListener(onTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    public ScrollTextView(Context context) {
        super(context);
        init(context);

    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * Initializes the related parameters
     *
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;

        if (TextUtils.isEmpty(mText)) {
            mText = "";
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(DEF_TEXT_SIZE);
    }


    public void setText(String text) {
        mText = text;
        if (TextUtils.isEmpty(mText)) {
            mText = "";
        }
        requestLayout();
        invalidate();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //获取屏幕上点击的坐标
                float x=event.getX();
                float y = event.getY();
                //如果坐标在我们的文字区域内，则将点击的文字改颜色
                if (x>mCoordinateX&&x<(mCoordinateX+mTextWidth)){
                    if (onTextClickListener!=null){
                        onTextClickListener.onTextClick(mText);
                    }
                }
                break;
        }
        //这句话不要修改
        return super.onTouchEvent(event);
    }

    /**
     * Set the text size, if this value is < 0, set to the default size
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        mPaint.setTextSize(mTextSize <= 0 ? DEF_TEXT_SIZE : mTextSize);
        requestLayout();
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        mPaint.setColor(mTextColor);
        invalidate();
    }

    /**
     * Set the text scrolling speed, if the value < 0, set to the default is 0
     *
     * @param speed If this value is 0, then stop scrolling
     */
    public void setTextSpeed(float speed) {
        this.mSpeed = speed < 0 ? 0 : speed;
        invalidate();
    }

    public float getTextSpeed() {
        return mSpeed;
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
        invalidate();
    }

    public boolean isScroll() {
        return isScroll;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextWidth = mPaint.measureText(mText);
        mCoordinateX = getPaddingLeft();
        mCoordinateY = getPaddingTop() + Math.abs(mPaint.ascent());
        mViewWidth = measureWidth(widthMeasureSpec);
        int mViewHeight = measureHeight(heightMeasureSpec);

        //If you do not call this method, will be thrown "IllegalStateException"
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mPaint.getTextSize() + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, mCoordinateX, mCoordinateY, mPaint);
        if (!isScroll) {
            return;
        }
        mCoordinateX -= mSpeed;
        if (Math.abs(mCoordinateX) > mTextWidth && mCoordinateX < 0) {
            mCoordinateX = mViewWidth;
        }
        invalidate();
    }
}
