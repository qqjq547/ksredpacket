package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.MyBidAdapter;
import com.guochuang.mimedia.ui.adapter.PictureVerticalAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBidActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_done_times)
    TextView tvDoneTimes;
    @BindView(R.id.tv_bid_times)
    TextView tvBidTimes;
    @BindView(R.id.lin_info)
    LinearLayout linInfo;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    MyBidAdapter adapter;
    List<String> dataArr=new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_bid;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_bid_buy);
        dataArr.add("");
        dataArr.add("");
        dataArr.add("");
        dataArr.add("");
        adapter=new MyBidAdapter(dataArr);
        rvRecord.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvRecord.setAdapter(adapter);
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
