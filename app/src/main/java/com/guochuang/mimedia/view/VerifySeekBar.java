package com.guochuang.mimedia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerifySeekBar extends SeekBar {
    //这两个值为用算法使用的2空间复杂度
    private int index = 150;
    private boolean k = true;

    public VerifySeekBar(Context context) {
        super(context);
    }

    public VerifySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerifySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            k = true;
            if (x - index > 20) {
                k = false;
                return true;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            if (!k){
                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }
}