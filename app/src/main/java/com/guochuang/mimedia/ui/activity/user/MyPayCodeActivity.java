package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.view.SquareImageView;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPayCodeActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_code)
    SquareImageView ivCode;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_identify)
    TextView tvIdentify;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_pay_code;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.pay_code_title);
       ivImage.setImageResource(R.drawable.ic_scan_white);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPref().getInt(PrefUtil.IDENTITY,0)==0){
            tvRecord.setVisibility(View.INVISIBLE);
            tvIdentify.setVisibility(View.VISIBLE);
        }else {
            tvRecord.setVisibility(View.VISIBLE);
            tvIdentify.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_image, R.id.iv_code, R.id.tv_save, R.id.tv_record,R.id.tv_identify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image:
                break;
            case R.id.iv_code:
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_record:
                break;
            case R.id.tv_identify:
                break;
        }
    }
}
