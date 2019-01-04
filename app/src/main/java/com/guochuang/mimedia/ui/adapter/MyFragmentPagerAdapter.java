package com.guochuang.mimedia.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    Fragment[] fragmentArr;
    String[] titleArr;
    FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm, Fragment[] fragmentArr, String[] titleArr) {
        super(fm);
        this.fm = fm;
        this.fragmentArr = fragmentArr;
        this.titleArr = titleArr;
    }

    @Override
    public int getCount() {
        return fragmentArr.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArr[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleArr==null){
            return "";
        }else{
            return titleArr[position];
        }

    }

    public long getItemId(int position) {
        int hashCode = fragmentArr[position].hashCode();
        return hashCode;
    }
}