package com.guochuang.mimedia.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyViewListAdapter extends PagerAdapter {

    private List<View> viewArr;
    private String[] titleArr;

    public MyViewListAdapter(List<View> viewArr) {
        this.viewArr=viewArr;
    }
    public MyViewListAdapter(List<View> viewArr, String[] titleArr) {
        this.viewArr=viewArr;
        this.titleArr=titleArr;
    }

    @Override
    public int getCount() {//必须实现
        return viewArr.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//必须实现
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
        container.addView(viewArr.get(position));
        return viewArr.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleArr==null)
        return super.getPageTitle(position);
        return titleArr[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView(viewArr.get(position));
    }
}