package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
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
    @BindView(R.id.lin_empty)
    LinearLayout linEmpty;


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


    @OnClick({R.id.tv_search,R.id.tv_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                startActivity(new Intent(getActivity(),InfoSearchActivity.class));
                break;
            case R.id.tv_refresh:
                showLoadingDialog(null);
                mvpPresenter.categoryGet(Constant.SYSTEM_CODE,Constant.TYPE_INFOMATION);
                break;
        }

    }

    @Override
    public void setData(List<Category> data) {
        closeLoadingDialog();
        linEmpty.setVisibility(View.GONE);
        vpInfo.setVisibility(View.VISIBLE);
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
        closeLoadingDialog();
       showShortToast(msg);
        linEmpty.setVisibility(View.VISIBLE);
        vpInfo.setVisibility(View.GONE);
    }
}
