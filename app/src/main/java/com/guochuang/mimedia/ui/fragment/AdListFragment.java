package com.guochuang.mimedia.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.ui.adapter.MyAdAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AdListFragment extends MvpFragment {
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
        rvAd.setAdapter(adAdapter);
    }

}
