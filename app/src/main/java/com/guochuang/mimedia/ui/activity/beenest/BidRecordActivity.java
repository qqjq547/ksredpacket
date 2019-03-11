package com.guochuang.mimedia.ui.activity.beenest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.presenter.BidHistoryPresenter;
import com.guochuang.mimedia.mvp.view.BidHistoryView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.BidRecordAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BidRecordActivity extends MvpActivity<BidHistoryPresenter> implements BidHistoryView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;

    BidRecordAdapter adapter;
    List<NestAuctionRecord> dataArr=new ArrayList<>();
    long nestLocationId=0;
    String startDate;

    @Override
    protected BidHistoryPresenter createPresenter() {
        return new BidHistoryPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bid_record;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.bid_record);
        nestLocationId= getIntent().getLongExtra(Constant.NESTLOCATIONID,0);
        startDate=getIntent().getStringExtra(Constant.STARTDATE);
        rvHistory.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new BidRecordAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvHistory.setAdapter(adapter);
        mvpPresenter.getNestAuctionHistory(nestLocationId,startDate);
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    public void setData(List<NestAuctionRecord> data) {
        dataArr.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
