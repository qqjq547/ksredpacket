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
import com.guochuang.mimedia.mvp.presenter.BidHistoryPresneter;
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

public class BidRecordActivity extends MvpActivity<BidHistoryPresneter> implements BidHistoryView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    BidRecordAdapter adapter;
    List<NestAuctionRecord> dataArr=new ArrayList<>();
    int curPage=1;
    long nestTimeId;

    @Override
    protected BidHistoryPresneter createPresenter() {
        return new BidHistoryPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bid_record;
    }

    @Override
    public void initViewAndData() {
          tvTitle.setText(R.string.bid_record);
        nestTimeId= getIntent().getLongExtra(Constant.NESTTIMEID,0);
        rvHistory.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new BidRecordAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvHistory.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getNestAuctionHistory(nestTimeId,curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getNestAuctionHistory(nestTimeId,1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getNestAuctionHistory(nestTimeId,curPage,Constant.PAGE_SIZE);
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    public void setData(Page<NestAuctionRecord> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            dataArr.clear();
        }
        if (data.getDataList() != null) {
            dataArr.addAll(data.getDataList());
        }
        adapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }
}
