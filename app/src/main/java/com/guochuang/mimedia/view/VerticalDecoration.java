package com.guochuang.mimedia.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;

import com.sz.gcyh.KSHongBao.R;


public class VerticalDecoration extends DividerItemDecoration {

    public VerticalDecoration(Context context) {
        super(context, OrientationHelper.VERTICAL);
        setDrawable(ContextCompat.getDrawable(context,R.drawable.bg_decoration));
    }
    public VerticalDecoration(Context context,int drawableId) {
        super(context, OrientationHelper.VERTICAL);
        setDrawable(ContextCompat.getDrawable(context,drawableId));
    }
}
