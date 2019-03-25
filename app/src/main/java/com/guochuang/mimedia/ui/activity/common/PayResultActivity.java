package com.guochuang.mimedia.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PaymentResult;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayResultActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_ksb)
    TextView tvKsb;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;

    PaymentResult result;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_pay_result;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.ksb_pay_title);
        result=(PaymentResult)getIntent().getSerializableExtra(Constant.DATA);
        if (result!=null) {
            GlideImgManager.loadCircleImage(this, result.getAvatar(), ivAvatar);
            tvNickname.setText(result.getNickName());
            tvRealname.setText(String.format(getString(R.string.format_kuahao), result.getRealName()));
            tvMoney.setText(String.format(getString(R.string.format_yuan), result.getMoney()));
            tvKsb.setText(String.format(getString(R.string.format_ksb), result.getCoin()));
            GlideImgManager.loadCornerImage(this, result.getTipsUrl(), ivBanner, CommonUtil.dip2px(this, 8));
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_banner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_banner:
                if (result!=null){
                    IntentUtils.startWebActivity(this,"",result.getTipsjumpUrl());
                }
                break;
        }
    }
}
