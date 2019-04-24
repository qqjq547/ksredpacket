package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.AAADetailedPresenter;
import com.guochuang.mimedia.mvp.view.AAADetailedView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sz.gcyh.KSHongBao.R;

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
        tvTitle.setText(getString(R.string.aaa_detailed_str));
        tvText.setText(getString(R.string.all));
    }



    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_all, R.id.tv_tranform_ksb, R.id.tv_ksb_tranform_aaa, R.id.tv_tibi, R.id.tv_coin_charging,R.id.iv_shomd})
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

                break;
            case R.id.tv_tranform_ksb:

                break;
            case R.id.tv_ksb_tranform_aaa:

                break;
            case R.id.tv_tibi:

                break;
            case R.id.tv_coin_charging:

                break;
            case R.id.iv_shomd:
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

}
