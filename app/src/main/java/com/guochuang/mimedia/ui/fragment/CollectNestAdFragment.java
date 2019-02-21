package com.guochuang.mimedia.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.model.NestFavorite;
import com.guochuang.mimedia.mvp.presenter.CollectInfoPresenter;
import com.guochuang.mimedia.mvp.presenter.CollectNestAdPresenter;
import com.guochuang.mimedia.mvp.view.CollectNestAdView;
import com.guochuang.mimedia.mvp.view.InfoListView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.activity.beenest.BeeNestActivity;
import com.guochuang.mimedia.ui.adapter.InfoAdapter;
import com.guochuang.mimedia.ui.adapter.NestFavoriteAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectNestAdFragment extends MvpFragment<CollectNestAdPresenter> implements CollectNestAdView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    List<NestFavorite> itemArr=new ArrayList<>();
    NestFavoriteAdapter adapter;
    int curPage=1;

    @Override
    protected CollectNestAdPresenter createPresenter() {
        return new CollectNestAdPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void initViewAndData() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.VERTICAL,false));
        rvList.addItemDecoration(new VerticalDecoration(getContext(),R.drawable.bg_decoration));
        adapter=new NestFavoriteAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NestFavorite item=itemArr.get(position);
                startActivity(new Intent(getActivity(),BeeNestActivity.class).putExtra(Constant.NESTINFOID,item.getNestInfoId()));
            }
        });
        rvList.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getNestAdFavorite(curPage+1,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getNestAdFavorite(1,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getNestAdFavorite(curPage,Constant.PAGE_SIZE);
    }

    @Override
    public void setData(Page<NestFavorite> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage=data.getCurrentPage();
        if (curPage==1){
            itemArr.clear();
        }
        if (data.getDataList() != null) {
            itemArr.addAll(data.getDataList());
            adapter.notifyDataSetChanged();
        }
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
        showShortToast(msg);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.REQUEST_COLLECT&&resultCode==Activity.RESULT_OK){
            int position=data.getIntExtra(Constant.POSITION,-1);
            if (position>=0&&position<itemArr.size()){
                itemArr.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }
    }
}
