package com.guochuang.mimedia.ui.activity.treasure;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.presenter.ExpressInfoPresenter;
import com.guochuang.mimedia.mvp.view.ExpressInfoView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpressInfoActivity extends MvpActivity<ExpressInfoPresenter> implements ExpressInfoView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.lin_not_send)
    LinearLayout linNotSend;
    @BindView(R.id.tv_express)
    TextView tvExpress;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.lin_has_send)
    LinearLayout linHasSend;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    Snatch snatch;
    long snatchId;
    @Override
    protected ExpressInfoPresenter createPresenter() {
        return new ExpressInfoPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_express_info;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.express_info);
        snatchId=getIntent().getLongExtra(Constant.SNATCHID,0);
         showLoadingDialog(null);
         mvpPresenter.getSnatchDetail(snatchId);
    }

    @OnClick({R.id.iv_back, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_copy:
                if (snatch!=null) {
                    CommonUtil.copyMsg(this, snatch.getExpressCode());
                    showShortToast(R.string.copy_success);
                }
                break;
        }
    }

    @Override
    public void setData(Snatch data) {
        closeLoadingDialog();
        GlideImgManager.loadImage(this,data.getSnatchImg(),ivIcon);
        tvAddress.setText(snatch.getProvince()+snatch.getCity()+snatch.getDistrict()+snatch.getAddress());
        if (snatch.getType()==1&&snatch.getStatus()>=4){
            linHasSend.setVisibility(View.VISIBLE);
            linNotSend.setVisibility(View.GONE);
            if (TextUtils.isEmpty(snatch.getExpressCode())){
                tvCopy.setVisibility(View.GONE);
            }else {
                tvCopy.setVisibility(View.VISIBLE);
            }
            tvExpress.setText(snatch.getExpressName()+"  "+snatch.getExpressCode());
        }else {
            linHasSend.setVisibility(View.GONE);
            linNotSend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}

