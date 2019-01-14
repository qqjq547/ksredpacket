package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RainRecord;
import com.guochuang.mimedia.mvp.presenter.WelfarePresenter;
import com.guochuang.mimedia.mvp.view.WelfareView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.adapter.WelfareAdapter;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WelfareActivity extends MvpActivity<WelfarePresenter> implements WelfareView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_welfare)
    RecyclerView rvWelfare;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    List<RainRecord> recordList = new ArrayList<>();
    WelfareAdapter adapter;
    int curPage = 1;

    @Override
    protected WelfarePresenter createPresenter() {
        return new WelfarePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welfare;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_welfare);
        rvWelfare.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rvWelfare.addItemDecoration(new VerticalDecoration(this));
        adapter = new WelfareAdapter(recordList);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RainRecord record = recordList.get(position);
                if (record.getState() == Constant.STATE_UN_GET) {
                    if (record.getQuantity() > 0 && record.getValidSecond() > 0)
                        IntentUtils.startRedbagRainActivityForResult(WelfareActivity.this, record.getValidSecond() * 1000, record.getQuantity(), record.getRedPacketRainUuid());
                }
            }
        });
        rvWelfare.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRainRecordList(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRainRecordList(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getRainRecordList(curPage, Constant.PAGE_SIZE);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<RainRecord> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            recordList.clear();
        }
        if (data.getDataList() != null) {
            recordList.addAll(data.getDataList());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            recordList.clear();
            adapter.notifyDataSetChanged();
            srlRefresh.autoRefresh();
        }
    }
}
