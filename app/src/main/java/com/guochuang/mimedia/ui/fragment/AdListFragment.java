package com.guochuang.mimedia.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.mvp.model.NestStatistics;
import com.guochuang.mimedia.mvp.presenter.AdListPresneter;
import com.guochuang.mimedia.mvp.view.AdListView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.activity.beenest.BidBrandActivity;
import com.guochuang.mimedia.ui.adapter.MyAdAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AdListFragment extends MvpFragment<AdListPresneter> implements AdListView {

    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_ad)
    RecyclerView rvAd;
    MyAdAdapter adapter;
    List<MyAd> dataArr =new ArrayList<>();
    Integer status;
    int curPage=1;

    @Override
    protected AdListPresneter createPresenter() {
        return new AdListPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_adlist;
    }

    @Override
    public void initViewAndData() {
        rvAd.setLayoutManager(new LinearLayoutManager(getActivity(),OrientationHelper.VERTICAL,false));
        adapter =new MyAdAdapter(dataArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyAd myAd=dataArr.get(position);
                if (myAd.getIsEdit()==0){
                    IntentUtils.startEditAdActivity(getActivity(),myAd.getNestInfoId(),myAd.getNestLocationId(),myAd.getNestTimeInfoId());
                }else {
                    startActivity(new Intent(getActivity(),BeeNestActivity.class).putExtra(Constant.NESTINFOID,myAd.getNestInfoId()));
                }
            }
        });
        rvAd.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getMyAdList(status,curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getMyAdList(status,1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getMyAdList(status,curPage,Constant.PAGE_SIZE);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void setData(Page<MyAd> data) {
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
