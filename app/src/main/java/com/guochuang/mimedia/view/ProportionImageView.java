package com.guochuang.mimedia.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.sz.gcyh.KSHongBao.R;


public class ProportionImageView extends AppCompatImageView {
    private float mWidthPro;
    private float mHeightPro;
    private int mCorners = 0;

    public ProportionImageView(Context context) {
        this(context, null);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ProportionImageView);
        mWidthPro = array.getFloat(
                R.styleable.ProportionImageView_width_proportion, mWidthPro);
        mHeightPro = array.getFloat(
                R.styleable.ProportionImageView_height_proportion, mHeightPro);
        mCorners = (int) array.getDimension(R.styleable.ProportionImageView_corners, mCorners);
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 先测量一下
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 用户没有设置宽高比就不处理
        if (mWidthPro == 0 || mHeightPro == 0) {
            return;
        }
        // 拿到宽

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = (int) (height * (mWidthPro * 1.0 / mHeightPro));
        // 宽和高按比例设置
        setMeasuredDimension(width, height);
    }


}
