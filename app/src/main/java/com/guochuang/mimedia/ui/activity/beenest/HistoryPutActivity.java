package com.guochuang.mimedia.ui.activity.beenest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.NestHistory;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.presenter.HistoryPutPresneter;
import com.guochuang.mimedia.mvp.view.HistoryPutView;
import com.guochuang.mimedia.ui.adapter.HistoryPutAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryPutActivity extends MvpActivity<HistoryPutPresneter> implements HistoryPutView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    HistoryPutAdapter adapter;
    List<NestHistory> dataArr=new ArrayList<>();
    int curPage=1;


    @Override
    protected HistoryPutPresneter createPresenter() {
        return new HistoryPutPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_history_put;
    }

    @Override
    public void initViewAndData() {
          tvTitle.setText(R.string.history_put);
        rvHistory.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new HistoryPutAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvHistory.setAdapter(adapter);
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


    @Override
    public void setData(Page<NestHistory> data) {
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
