package com.guochuang.mimedia.tools;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CheckConfig {
    EditText etAmout;
    EditText etCount;
    CallBack mCallBack;
    View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //失去焦点的时候
                String amoutText = etAmout.getText().toString().trim();
                String countText = etCount.getText().toString().trim();

                if (TextUtils.isEmpty(amoutText) || TextUtils.isEmpty(countText)) {
                    return;
                }
                try {
                    double amout = Double.valueOf(amoutText);
                    int count = Integer.valueOf(countText);
                    if (mCallBack != null) {
                        mCallBack.gotoChek(amout, count);
                    }
                } catch (Exception e) {
                    if (mCallBack != null) {
                        mCallBack.onErro(e);
                    }
                    return;
                }


            }
            //有焦点了
            mCallBack.hasFocus(v, hasFocus);

        }
    };

    public void check(EditText etAmout, EditText etCount, CallBack callBack) {
        this.etAmout = etAmout;
        this.etCount = etCount;
        mCallBack = callBack;
        etAmout.setOnFocusChangeListener(mOnFocusChangeListener);
        etCount.setOnFocusChangeListener(mOnFocusChangeListener);
    }

    public interface CallBack {
        void gotoChek(double amout, int count);

        void onErro(Exception e);

        void hasFocus(View v, boolean hasFocus);
    }

}
