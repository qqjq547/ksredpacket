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
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.presenter.AdListPresenter;
import com.guochuang.mimedia.mvp.presenter.MyTreasurePresenter;
import com.guochuang.mimedia.mvp.presenter.TreasurePresenter;
import com.guochuang.mimedia.mvp.view.AdListView;
import com.guochuang.mimedia.mvp.view.MyTreasureView;
import com.guochuang.mimedia.mvp.view.TreasureView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.UrlConfig;
import com.guochuang.mimedia.ui.activity.treasure.ExpressInfoActivity;
import com.guochuang.mimedia.ui.activity.treasure.MyTreasureActivity;
import com.guochuang.mimedia.ui.activity.treasure.ShowListActivity;
import com.guochuang.mimedia.ui.activity.user.MyAddressActivity;
import com.guochuang.mimedia.ui.adapter.MyAdAdapter;
import com.guochuang.mimedia.ui.adapter.MyTreasureAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TreasureFragment extends MvpFragment<TreasurePresenter> implements TreasureView {

    @BindView(R.id.rv_treasure)
    RecyclerView rvTreasure;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    MyTreasureAdapter adapter;
    List<Snatch> dataArr=new ArrayList<>();
    int curPage=1;
    boolean isWin=true;

    @Override
    protected TreasurePresenter createPresenter() {
        return new TreasurePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_treasure;
    }

    @Override
    public void initViewAndData() {
        rvTreasure.setLayoutManager(new LinearLayoutManager(getActivity(),OrientationHelper.VERTICAL,false));
        adapter=new MyTreasureAdapter(dataArr);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Snatch snatch=dataArr.get(position);
                switch (view.getId()){
                    case R.id.tv_address:
                        if (snatch.getShowAddress()==1) {
                            startActivityForResult(new Intent(getActivity(), MyAddressActivity.class).putExtra(Constant.SNATCHID,snatch.getSnatchId()), Constant.REQUEST_PICK_ADDRESS);
                        }
                        break;
                    case R.id.tv_comment:
                        startActivityForResult(new Intent(getActivity(), ShowListActivity.class).putExtra(Constant.SNATCH,snatch),Constant.REQUEST_SET_SHOWLIST);
                        break;
                    case R.id.tv_express:
                        startActivity(new Intent(getActivity(), ExpressInfoActivity.class).putExtra(Constant.SNATCHID, snatch.getSnatchId()));
                        break;
                    case R.id.lin_join_people_time:
                        IntentUtils.startWebActivity(getActivity(),"",UrlConfig.getHtmlUrl(UrlConfig.URL_DUOBAO_TREASURE_NUMBER)+snatch.getSnatchRecordId());
                        break;
                    case R.id.tv_pay:
                        showLoadingDialog(null);
                        ((MyTreasureActivity)getActivity()).getOrder(snatch.getOrderId());
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startWebActivity(getActivity(),"",UrlConfig.getHtmlUrl(UrlConfig.URL_DUOBAO_DETAIL)+dataArr.get(position).getSnatchId());
            }
        });
        rvTreasure.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRecordList(curPage+1,Constant.PAGE_SIZE,isWin);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRecordList(1,Constant.PAGE_SIZE,isWin);
            }
        });
        mvpPresenter.getRecordList(1,Constant.PAGE_SIZE,isWin);
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public void autoRefresh(){
        if (isAdded()){
            srlRefresh.autoRefresh();
        }
    }

    @Override
    public void setData(Page<Snatch> data) {
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
