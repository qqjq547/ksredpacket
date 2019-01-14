package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.ui.activity.BidBrandActivity;
import com.guochuang.mimedia.ui.activity.MyAdActivity;
import com.guochuang.mimedia.ui.adapter.MyAdAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdListFragment extends MvpFragment {

    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_ad)
    RecyclerView rvAd;
    MyAdAdapter adAdapter;
    List<MyAd> myAdArr=new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_adlist;
    }

    @Override
    public void initViewAndData() {
       rvAd.setLayoutManager(new LinearLayoutManager(getActivity(),OrientationHelper.VERTICAL,false));
        MyAd myAd=new MyAd();
        myAd.setState(0);
        MyAd myAd1=new MyAd();
        myAd1.setState(1);
        MyAd myAd2=new MyAd();
        myAd2.setState(2);
        myAdArr.add(myAd);
        myAdArr.add(myAd1);
        myAdArr.add(myAd2);
        adAdapter=new MyAdAdapter(myAdArr);
        adAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(),BidBrandActivity.class));
            }
        });
        rvAd.setAdapter(adAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }

}
