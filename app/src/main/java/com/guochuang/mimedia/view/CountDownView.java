package com.guochuang.mimedia.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;


public class CountDownView extends TextView {
    int mTime = 30;
    int defultColor = Color.parseColor("#CC000000");

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.sendEmptyMessageDelayed(0,1000);
            if(mTime > 0) {
                --mTime;
                setText(mTime + "");
            }else {
                setVisibility(GONE);
            }

        }
    };

    public CountDownView(Context context) {
        this(context,null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intit();
    }


    private void intit() {
        setBackgroundColor(defultColor);
        setText(mTime + "");
    }


    private void setTime(int time) {
        mTime = time;
        setVisibility(VISIBLE);
        setText(mTime + "");
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(0,1000);
    }


    public int getCurrentTime(){
        return mTime;
    }

}
