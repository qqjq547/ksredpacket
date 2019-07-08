package com.guochuang.mimedia.ui.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.ShareBenefit;
import com.guochuang.mimedia.mvp.presenter.ShareBenefitDetailPresenter;
import com.guochuang.mimedia.mvp.view.ShareBenefitDetailView;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.ShareBenefitAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareBenefitDetailActivity extends MvpActivity<ShareBenefitDetailPresenter> implements ShareBenefitDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<ShareBenefit> itemArr=new ArrayList<>();
    ShareBenefitAdapter adapter;
    String defaultIndex="0";
    String startIndex=defaultIndex;

    @Override
    protected ShareBenefitDetailPresenter createPresenter() {
        return new ShareBenefitDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sharebenefit_detail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.share_benefit_detail);
        rvRecord.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter=new ShareBenefitAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvRecord.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getKsbBonusDetailsList(startIndex,Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                startIndex=defaultIndex;
                mvpPresenter.getKsbBonusDetailsList(startIndex,Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getKsbBonusDetailsList(startIndex,Constant.PAGE_SIZE);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(List<ShareBenefit> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (startIndex.equals(defaultIndex)){
            itemArr.clear();
        }
        if (data==null||data.size()<Constant.PAGE_SIZE){
            startIndex=defaultIndex;
            srlRefresh.setEnableLoadmore(false);
            itemArr.addAll(data);
        }else {
            startIndex = data.get(data.size()-1).getStartIndex();
            itemArr.addAll(data);
            srlRefresh.setEnableLoadmore(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
