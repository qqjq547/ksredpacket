package com.guochuang.mimedia.ui.activity.info;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.presenter.InfoSearchPresenter;
import com.guochuang.mimedia.mvp.view.InfoListView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.InfoAdapter;
import com.guochuang.mimedia.view.ClearEditText;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.guochuang.mimedia.view.flowtag.FlowTagLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoSearchActivity extends MvpActivity<InfoSearchPresenter> implements InfoListView {
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.lin_hint)
    LinearLayout linHint;
    @BindView(R.id.ftl_hot)
    FlowTagLayout ftlHot;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.lin_result)
    LinearLayout linResult;

    List<InfoItem> infoItems =new ArrayList<>();
    InfoAdapter infoAdapter;

    int curPage=1;
    String title;

    @Override
    protected InfoSearchPresenter createPresenter() {
        return new InfoSearchPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_info_search;
    }

    @Override
    public void initViewAndData() {
        infoAdapter=new InfoAdapter(infoItems);
        infoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        infoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InfoItem item= infoItems.get(position);
                IntentUtils.startInfoDetailActivity(InfoSearchActivity.this,item.getAuthor(),item.getArticleUuid());
            }
        });
        rvInfo.setLayoutManager(new LinearLayoutManager(InfoSearchActivity.this,OrientationHelper.VERTICAL,false));
        rvInfo.addItemDecoration(new VerticalDecoration(InfoSearchActivity.this));
        rvInfo.setItemAnimator(new DefaultItemAnimator());
        rvInfo.setAdapter(infoAdapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRecommendList(curPage+1,Constant.PAGE_SIZE,title);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRecommendList(1,Constant.PAGE_SIZE,title);
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword=etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)){
                        title=keyword;
                        showLoadingDialog(null);
                        mvpPresenter.getRecommendList(1,Constant.PAGE_SIZE,title);
                        CommonUtil.hideInput(InfoSearchActivity.this,etSearch);
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<InfoItem> data) {
        closeLoadingDialog();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        infoAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        if (data!=null) {
            curPage = data.getCurrentPage();
            if (curPage == 1) {
                infoItems.clear();
            }
            infoItems.addAll(data.getDataList());
            infoAdapter.setKeyWord(title);

            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showLoadingDialog(msg);
    }
}
