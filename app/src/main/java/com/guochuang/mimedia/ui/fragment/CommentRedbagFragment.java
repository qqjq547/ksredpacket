package com.guochuang.mimedia.ui.fragment;

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
import com.guochuang.mimedia.mvp.presenter.CommentRedbagPresenter;
import com.guochuang.mimedia.mvp.view.CommentRedbagView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.CommentRedbagAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommentRedbagFragment extends MvpFragment<CommentRedbagPresenter> implements CommentRedbagView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<CommentRedbag> itemArr = new ArrayList<>();
    CommentRedbagAdapter adapter;
    int curPage = 1;
    int deletePos = 0;

    @Override
    protected CommentRedbagPresenter createPresenter() {
        return new CommentRedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void initViewAndData() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        rvList.addItemDecoration(new VerticalDecoration(getContext(), R.drawable.bg_decoration));
        adapter = new CommentRedbagAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(itemArr.get(position).getRoleType())){
                    IntentUtils.startRedbagDetailActivity(getActivity(),itemArr.get(position).getRedPacketUuid(),itemArr.get(position).getRoleType(),null);
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                deletePos = position;
                showLoadingDialog(null);
                mvpPresenter.redBagCommentDelete(itemArr.get(position).getCommentId());
            }
        });
        rvList.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getUserRedbagComment(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getUserRedbagComment(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getUserRedbagComment(curPage, Constant.PAGE_SIZE);
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
    public void setCommentDelete(Boolean data) {
        closeLoadingDialog();
        itemArr.remove(deletePos);
        adapter.notifyItemRemoved(deletePos);
    }
}
