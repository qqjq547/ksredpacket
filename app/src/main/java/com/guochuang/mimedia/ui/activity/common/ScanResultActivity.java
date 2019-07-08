package com.guochuang.mimedia.ui.activity.common;

import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanResultActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_scan_result;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.scan_result);
      String content= getIntent().getStringExtra(Constant.DATA);
      tvResult.setText(content);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
