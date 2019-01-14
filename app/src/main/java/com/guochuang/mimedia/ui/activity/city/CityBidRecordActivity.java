package com.guochuang.mimedia.ui.activity.city;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CityBidRecord;
import com.guochuang.mimedia.mvp.model.RainRecord;
import com.guochuang.mimedia.mvp.presenter.CityBidRecordPresenter;
import com.guochuang.mimedia.mvp.view.CityBidRecordView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.CityBidRecordAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityBidRecordActivity extends MvpActivity<CityBidRecordPresenter> implements CityBidRecordView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;

    ArrayList<CityBidRecord> itemArr = new ArrayList<>();
    CityBidRecordAdapter cityBidRecordAdapter;
    int curPage=1;
    long regionId;

    @Override
    protected CityBidRecordPresenter createPresenter() {
        return new CityBidRecordPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_city_bid_record;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.buy_record));
        regionId=getIntent().getLongExtra(Constant.REGIONID,0);
        cityBidRecordAdapter = new CityBidRecordAdapter(itemArr);
        cityBidRecordAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        cityBidRecordAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(cityBidRecordAdapter);
        srl.setEnableRefresh(true);
        srl.setEnableLoadmore(true);
        srl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getBidRecord(curPage+1,Constant.PAGE_SIZE,regionId);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getBidRecord(1,Constant.PAGE_SIZE,regionId);
            }
        });
        mvpPresenter.getBidRecord(1,Constant.PAGE_SIZE,regionId);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }

    }

    @Override
    public void setData(Page<CityBidRecord> data) {
        srl.finishRefresh();
        srl.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            itemArr.clear();
        }
        if (data.getDataList() != null) {
            itemArr.addAll(data.getDataList());
        }
        cityBidRecordAdapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srl.setEnableLoadmore(false);
        } else {
            srl.setEnableLoadmore(true);
        }
    }

    @Override
    public void setError(String msg) {
        srl.finishRefresh();
        srl.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }
}
