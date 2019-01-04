package com.guochuang.mimedia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.CommonUtil;


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
