package com.guochuang.mimedia.ui.activity.city;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.presenter.BidPricePresenter;
import com.guochuang.mimedia.mvp.view.BidPriceView;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.BuyPriceAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BidPriceActivity extends MvpActivity<BidPricePresenter> implements BidPriceView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<BidPrice> itemArr=new ArrayList<>();
    BuyPriceAdapter adapter;
    int curPage=1;

    @Override
    protected BidPricePresenter createPresenter() {
        return new BidPricePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bid_price;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.bid_price);
        tvText.setText(R.string.unit_ksb);
        rvRecord.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new BuyPriceAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvRecord.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getBidPrice(curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getBidPrice(1,Constant.PAGE_SIZE);
            }
        });
      mvpPresenter.getBidPrice(curPage,Constant.PAGE_SIZE);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<BidPrice> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (data!=null) {
            curPage = data.getCurrentPage();
            if (curPage == 1) {
                itemArr.clear();
            }
            if (data.getDataList() != null) {
                itemArr.addAll(data.getDataList());
            }
            adapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
