package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.guochuang.mimedia.mvp.model.RedbagBenefit;
import com.guochuang.mimedia.mvp.model.RedbagTotalBenefit;
import com.guochuang.mimedia.mvp.presenter.RedbagBenefitPresenter;
import com.guochuang.mimedia.mvp.view.RedbagBenefitView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.ui.adapter.RedbagBenefitAdapter;
import com.guochuang.mimedia.ui.dialog.BenefitCityDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedbagBenefitActivity extends MvpActivity<RedbagBenefitPresenter> implements RedbagBenefitView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_benefit)
    TextView tvBenefit;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_benefit)
    RecyclerView rvBenefit;

    List<RedbagBenefit> itemArr=new ArrayList<>();
    RedbagBenefitAdapter adapter;
    List<BenefitType> typeArr=new ArrayList<>();
    int curPage=1;
    String cityName="";
    String districtName="";
    BenefitCityDialog dialog;
    @Override
    protected RedbagBenefitPresenter createPresenter() {
        return new RedbagBenefitPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_redbag_benefit;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.redbag_benefit);
        tvText.setText(R.string.all);
        tvText.setVisibility(View.GONE);
        rvBenefit.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvBenefit.setItemAnimator(new DefaultItemAnimator());
        adapter=new RedbagBenefitAdapter(itemArr);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvBenefit.setAdapter(adapter);
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getRedbagBenefit(curPage+1,Constant.PAGE_SIZE,cityName,districtName);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getRedbagBenefit(1,Constant.PAGE_SIZE,cityName,districtName);
            }
        });
        mvpPresenter.getBidPrice();
        mvpPresenter.getRedbagBenefit(curPage,Constant.PAGE_SIZE,cityName,districtName);
    }

    @OnClick({R.id.iv_back, R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                if (typeArr.size()>0) {
                    if (dialog == null) {
                        dialog = new BenefitCityDialog(this, typeArr, new BenefitCityDialog.OnCheckListener() {
                            @Override
                            public void onChecked(String cityName, String districtName) {
                                RedbagBenefitActivity.this.cityName = cityName;
                                RedbagBenefitActivity.this.districtName = districtName;
                                if (TextUtils.isEmpty(cityName)&&TextUtils.isEmpty(districtName)){
                                    tvText.setText(R.string.check_all);
                                }else {
                                    tvText.setText(districtName);
                                }
                                mvpPresenter.getRedbagBenefit(1, Constant.PAGE_SIZE, cityName, districtName);
                            }
                        });
                    }
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void setData(List<BenefitType> data) {
        if (data!=null){
            typeArr.addAll(data);
        }
    }

    @Override
    public void setPageData(Page<RedbagBenefit> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        if (data!=null) {
            curPage = data.getCurrentPage();
            if (curPage == 1) {
                itemArr.clear();
            }
            if (data.getDataList() != null) {
                itemArr.addAll(data.getDataList());
            }
            adapter.notifyDataSetChanged();
            if (data.getCurrentPage() >= data.getTotalPage()) {
                srlRefresh.setEnableLoadmore(false);
            } else {
                srlRefresh.setEnableLoadmore(true);
            }
            if (data.getMap()!=null){
                tvBenefit.setText(data.getMap().getTotalCoin());
            }
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        showShortToast(msg);
    }
}
