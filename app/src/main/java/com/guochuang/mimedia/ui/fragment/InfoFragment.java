package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.presenter.InfoPresenter;
import com.guochuang.mimedia.mvp.view.InfoView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.InfoSearchActivity;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoFragment extends MvpFragment<InfoPresenter> implements InfoView {

    @BindView(R.id.tl_title)
    TabLayout tlTitle;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.vp_info)
    ViewPager vpInfo;

    InfoListFragment[] fragments;

    @Override
    protected InfoPresenter createPresenter() {
        return new InfoPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_info;
    }

    @Override
    public void initViewAndData() {
          mvpPresenter.categoryGet(Constant.SYSTEM_CODE,Constant.TYPE_INFOMATION);
    }


    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),InfoSearchActivity.class));
    }

    @Override
    public void setData(List<Category> data) {
        if (data.size()>0){
            fragments=new InfoListFragment[data.size()];
            String[] titles=new String[data.size()];
            for (int i=0;i<data.size();i++){
                fragments[i]=new InfoListFragment();
                fragments[i].setCategoryId(data.get(i).getId());
                titles[i]=data.get(i).getName();
            }
            vpInfo.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),fragments,titles));
            tlTitle.setupWithViewPager(vpInfo);
        }

    }

    @Override
    public void setError(String msg) {
       showShortToast(msg);
//        fragments=new InfoListFragment[1];
//        String[] titles=new String[1];
//        fragments[0]=new InfoListFragment();
//        fragments[0].setCategoryId(2);
//        titles[0]="本地";
//        vpInfo.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),fragments,titles));
//        tlTitle.setupWithViewPager(vpInfo);
    }
}
