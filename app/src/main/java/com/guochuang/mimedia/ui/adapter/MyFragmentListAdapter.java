package com.guochuang.mimedia.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentListAdapter extends FragmentPagerAdapter {
    List<?> fragmentArr;
    String[] titles;
    FragmentManager fm;

    public MyFragmentListAdapter(FragmentManager fm, List<? extends Fragment> fragmentArr) {
        super(fm);
        this.fm = fm;
        this.fragmentArr = fragmentArr;
    }
    public MyFragmentListAdapter(FragmentManager fm, List<? extends Fragment> fragmentArr, String[] titles) {
        super(fm);
        this.fm = fm;
        this.fragmentArr = fragmentArr;
        this.titles=titles;
    }

    @Override
    public int getCount() {
        return fragmentArr.size();
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment)fragmentArr.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles==null?"":titles[position];

    }

    public long getItemId(int position) {
        int hashCode = fragmentArr.get(position).hashCode();
        return hashCode;
    }
}