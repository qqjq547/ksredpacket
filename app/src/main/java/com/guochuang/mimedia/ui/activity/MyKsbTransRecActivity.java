package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.view.MyKsbTransRecView;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MyKsbTransRec;
import com.guochuang.mimedia.mvp.presenter.MyKsbTransRecPresenter;
import com.guochuang.mimedia.ui.adapter.MyKsbTransRecAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyKsbTransRecActivity extends MvpActivity<MyKsbTransRecPresenter> implements MyKsbTransRecView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    MyKsbTransRecAdapter myKsbTransRecAdapter;
    ArrayList<MyKsbTransRec> myKsbTransRecs = new ArrayList<>();
    int curPage = 1;

    @Override
    protected MyKsbTransRecPresenter createPresenter() {
        return new MyKsbTransRecPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ksb_trans_rec;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_ksb_ksb_transfer_record));

        if (myKsbTransRecAdapter != null) {
            return;
        }
        myKsbTransRecAdapter = new MyKsbTransRecAdapter(myKsbTransRecs);
        myKsbTransRecAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
        myKsbTransRecAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(myKsbTransRecAdapter);
        srl.setEnableRefresh(true);
        srl.setEnableLoadmore(true);
        srl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.userWithdrawalsGet(
                        curPage + 1,
                        Constant.PAGE_SIZE
                );
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.userWithdrawalsGet(
                        1,
                        Constant.PAGE_SIZE
                );
            }
        });
        mvpPresenter.userWithdrawalsGet(
                1,
                Constant.PAGE_SIZE
        );
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<MyKsbTransRec> data) {
        srl.finishRefresh();
        srl.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            myKsbTransRecs.clear();
        }
        if (data.getDataList() != null) {
            myKsbTransRecs.addAll(data.getDataList());
        }
        myKsbTransRecAdapter.notifyDataSetChanged();
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
        showShortToast(msg);
    }
}
