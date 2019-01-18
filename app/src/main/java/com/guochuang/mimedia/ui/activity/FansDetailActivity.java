package com.guochuang.mimedia.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.presenter.FansPresenter;
import com.guochuang.mimedia.mvp.view.FansView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.FansDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FansDetailActivity extends MvpActivity<FansPresenter> implements FansView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<RecommedUser> itemArr=new ArrayList<>();
    FansDetailAdapter adapter;
    int curPage;
    @Override
    protected FansPresenter createPresenter() {
        return new FansPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_fans_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.fans_detail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new FansDetailAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvDetail.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getFans(curPage+1,Constant.PAGE_SIZE,Constant.SORT_DEFAULT);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getFans(1,Constant.PAGE_SIZE,Constant.SORT_DEFAULT);
            }
        });
        mvpPresenter.getFans(1,Constant.PAGE_SIZE,Constant.SORT_DEFAULT);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<RecommedUser> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
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

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }
}
