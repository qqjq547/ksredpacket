package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.AAADetail;
import com.guochuang.mimedia.mvp.presenter.AAADetailedPresenter;
import com.guochuang.mimedia.mvp.view.AAADetailedView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.AAADetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AAA 明细
 */
public class AAADetailedActivity extends MvpActivity<AAADetailedPresenter> implements AAADetailedView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.rv_aaalist)
    RecyclerView rvAaalist;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_tranform_ksb)
    TextView tvTranformKsb;
    @BindView(R.id.tv_ksb_tranform_aaa)
    TextView tvKsbTranformAaa;
    @BindView(R.id.tv_tibi)
    TextView tvTibi;
    @BindView(R.id.tv_coin_charging)
    TextView tvCoinCharging;

    @BindView(R.id.fl_select_view)
    FrameLayout mFlSelectView;

    private static final int pageSize = 20;
    private int currentPage = 0;
    private int mCurrentType;
    private List<AAADetail> mData = new ArrayList<AAADetail>();
    private AAADetailAdapter mAAADetailAdapter;

    @Override

    protected AAADetailedPresenter createPresenter() {
        return new AAADetailedPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_aaadetailed;
    }

    @Override
    public void initViewAndData() {
        closeSelectView();
        showLoadingDialog(null);
        getAAADetailedList(currentPage, pageSize, 0);
        tvTitle.setText(getString(R.string.aaa_detailed_str));
        tvText.setText(getString(R.string.all));
        srlRefresh.setOnRefreshListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 0;
                mData.clear();
                getAAADetailedList(currentPage, pageSize, mCurrentType);
            }


            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++currentPage;
                getAAADetailedList(currentPage, pageSize, mCurrentType);

            }

        });
        srlRefresh.setEnableLoadmore(true);
        rvAaalist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAAADetailAdapter = new AAADetailAdapter(mData);
        mAAADetailAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        mAAADetailAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvAaalist.setAdapter(mAAADetailAdapter);
    }

    /**
     * 获取AAA 明细列表
     */
    private void getAAADetailedList(int currentPage, int pageSize, int type) {
        showLoadingDialog(null);
        mvpPresenter.getAAADetailedList(currentPage, pageSize, type==0? null:String.valueOf(type));
    }


    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_all, R.id.tv_tranform_ksb, R.id.tv_ksb_tranform_aaa,
            R.id.tv_tibi, R.id.tv_coin_charging, R.id.iv_shomd})
    public void onViewClicked(View view) {
        closeSelectView();
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                openSelectView();
                break;
            case R.id.tv_all:
                mCurrentType=0;
                mData.clear();
                getAAADetailedList(currentPage, pageSize,0);
                break;
            case R.id.tv_tranform_ksb:
                mData.clear();
                mCurrentType = Constant.AAA2KSB;
                getAAADetailedList(currentPage, pageSize, Constant.AAA2KSB);
                break;
            case R.id.tv_ksb_tranform_aaa:
                mData.clear();
                mCurrentType = Constant.KSB2AAA;
                getAAADetailedList(currentPage, pageSize, Constant.KSB2AAA);
                break;
            case R.id.tv_tibi:
                mData.clear();
                mCurrentType = Constant.EXTRACT_AAA;
                getAAADetailedList(currentPage, pageSize, Constant.EXTRACT_AAA);
                break;
            case R.id.tv_coin_charging:
                mData.clear();
                mCurrentType = Constant.Fill_AAA;
                getAAADetailedList(currentPage, pageSize, Constant.Fill_AAA);
                break;

        }

    }

    /**
     * 打开 选择 View
     */
    private void openSelectView() {
        mFlSelectView.setVisibility(View.VISIBLE);
    }

    private void closeSelectView() {
        mFlSelectView.setVisibility(View.GONE);
    }

    @Override
    public void setError(String message) {
        closeLoadingDialog();
        showShortToast(message);
        closeRefresAndLoadmore();
    }

    @Override
    public void setData(Page<AAADetail> data) {
        closeRefresAndLoadmore();
        closeLoadingDialog();
        mData.addAll(data.getDataList());
        mAAADetailAdapter.notifyDataSetChanged();
    }

    private void closeRefresAndLoadmore() {
        if (srlRefresh != null) {
            srlRefresh.finishLoadmore();
            srlRefresh.finishRefresh();
        }
    }

}
