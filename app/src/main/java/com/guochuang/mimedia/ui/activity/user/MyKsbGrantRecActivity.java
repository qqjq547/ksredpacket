package com.guochuang.mimedia.ui.activity.user;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MyKsbGrantRec;
import com.guochuang.mimedia.mvp.presenter.MyKsbGrantRecPresneter;
import com.guochuang.mimedia.mvp.view.MyKsbGrantRecView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.MyKsbGrantRecAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyKsbGrantRecActivity extends MvpActivity<MyKsbGrantRecPresneter> implements MyKsbGrantRecView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    MyKsbGrantRecAdapter myKsbGrantRecAdapter;
    int curPage = 1;
    List<MyKsbGrantRec> myKsbGrantRecs = new ArrayList<>();

    @Override
    protected MyKsbGrantRecPresneter createPresenter() {
        return new MyKsbGrantRecPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_ksb_grant_rec;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getResources().getString(R.string.my_ksb_ksb_grant_record));

        if (myKsbGrantRecAdapter != null) {
            return;
        }
        myKsbGrantRecAdapter = new MyKsbGrantRecAdapter(myKsbGrantRecs);
        myKsbGrantRecAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        myKsbGrantRecAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(myKsbGrantRecAdapter);
        srl.setEnableRefresh(true);
        srl.setEnableLoadmore(true);
        srl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.userGetTransferRec(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.userGetTransferRec(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.userGetTransferRec(curPage, Constant.PAGE_SIZE);
    }

    @Override
    public void setData(Page<MyKsbGrantRec> data) {
        srl.finishRefresh();
        srl.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            myKsbGrantRecs.clear();
        }
        if (data.getDataList() != null) {
            myKsbGrantRecs.addAll(data.getDataList());
            myKsbGrantRecAdapter.notifyDataSetChanged();
        }
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
