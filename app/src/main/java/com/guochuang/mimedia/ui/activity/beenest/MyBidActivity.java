package com.guochuang.mimedia.ui.activity.beenest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.model.NestHistory;
import com.guochuang.mimedia.mvp.model.Square;
import com.guochuang.mimedia.mvp.presenter.MyBidPresenter;
import com.guochuang.mimedia.mvp.view.MyBidView;
import com.guochuang.mimedia.tools.Constant;
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

public class MyBidActivity extends MvpActivity<MyBidPresenter> implements MyBidView {
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
    List<NestAuctionRecord> dataArr=new ArrayList<>();
    int curPage=1;

    @Override
    protected MyBidPresenter createPresenter() {
        return new MyBidPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_bid;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_bid_buy);
        adapter=new MyBidAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvRecord.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvRecord.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getMyAuctionList(curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getMyAuctionList(1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getMyAuctionList(curPage,Constant.PAGE_SIZE);
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
            if (curPage==1&&dataArr.size()>0){
                tvDoneTimes.setText(String.valueOf(dataArr.get(0).getDealNum()));
                tvBidTimes.setText(String.valueOf(dataArr.get(0).getAuctionNum()));
            }
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
