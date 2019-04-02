package com.guochuang.mimedia.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CommentRedbag;
import com.guochuang.mimedia.mvp.presenter.CollectRedbagPresenter;
import com.guochuang.mimedia.mvp.view.CollectRedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.CollectRedbagAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectRedbagFragment extends MvpFragment<CollectRedbagPresenter> implements CollectRedbagView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<CommentRedbag> itemArr = new ArrayList<>();
    CollectRedbagAdapter adapter;
    int curPage = 1;

    @Override
    protected CollectRedbagPresenter createPresenter() {
        return new CollectRedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void initViewAndData() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        rvList.addItemDecoration(new VerticalDecoration(getContext(), R.drawable.bg_decoration));
        adapter = new CollectRedbagAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentRedbag redbag=itemArr.get(position);
                if (!TextUtils.isEmpty(redbag.getRoleType())){
                    IntentUtils.startRedbagDetailActivityForResult(getActivity(),redbag.getRedPacketUuid(),redbag.getRoleType(),redbag.getRedPacketType(),position);
                }
            }
        });
        rvList.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getUserRedbagCollect(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getUserRedbagCollect(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getUserRedbagCollect(curPage, Constant.PAGE_SIZE);
    }

    @Override
    public void setData(Page<CommentRedbag> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
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
        closeLoadingDialog();
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
