package com.guochuang.mimedia.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.presenter.InfoListPresenter;
import com.guochuang.mimedia.mvp.view.InfoListView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.InfoAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.objectbox.Box;

public class InfoListFragment extends MvpFragment<InfoListPresenter> implements InfoListView {
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;

    List<InfoItem> infoItems =new ArrayList<>();
    InfoAdapter infoAdapter;

    int curPage=1;
    int categoryId=0;
//    Box<InfoItem> box;

    @Override
    protected InfoListPresenter createPresenter() {
        return new InfoListPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_info_list;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void initViewAndData() {
        if (infoAdapter!=null){
            return;
        }
//        box =App.getInstance().getBoxStore().boxFor(InfoItem.class);
//        infoItems.addAll(box.find(InfoItem_.categoryId,categoryId));
        infoAdapter=new InfoAdapter(infoItems);
        infoAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        infoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InfoItem item= infoItems.get(position);
                IntentUtils.startInfoDetailActivity(getActivity(),item.getAuthor(),item.getArticleUuid());
            }
        });
        infoAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        rvInfo.setLayoutManager(new LinearLayoutManager(getContext(),OrientationHelper.VERTICAL,false));
        rvInfo.addItemDecoration(new VerticalDecoration(getContext()));
        rvInfo.setItemAnimator(new DefaultItemAnimator());
        rvInfo.setAdapter(infoAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRecommendList(curPage+1,Constant.PAGE_SIZE,categoryId);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRecommendList(1,Constant.PAGE_SIZE,categoryId);
            }
        });
//        mvpPresenter.getRecommendList(curPage,Constant.PAGE_SIZE,categoryId);
    }

    @Override
    public void setData(Page<InfoItem> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage=data.getCurrentPage();
        if (curPage==1){
            infoItems.clear();
//            box.remove(box.find(InfoItem_.categoryId,categoryId));
//            box.put(data.getDataList());
        }
        infoItems.addAll(data.getDataList());
        infoAdapter.notifyDataSetChanged();
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
}
