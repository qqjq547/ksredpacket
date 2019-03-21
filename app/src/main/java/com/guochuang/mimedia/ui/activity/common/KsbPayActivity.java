package com.guochuang.mimedia.ui.activity.common;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KsbPayActivity extends MvpActivity {
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
    @BindView(R.id.tv_my_ksb)
    TextView tvMyKsb;
    @BindView(R.id.lin_head)
    LinearLayout linHead;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ksb_pay;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.ksb_pay_title);
    }

    @OnClick({R.id.iv_back, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_sure:
                String moneyStr=etMoney.getText().toString().trim();
                String remark=etRemark.getText().toString().trim();

                break;
        }
    }
}
