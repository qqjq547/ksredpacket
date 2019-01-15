package com.guochuang.mimedia.ui.activity.treasure;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpressInfoActivity extends MvpActivity {
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_express_info;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.express_info);

    }

    @OnClick({R.id.iv_back, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_copy:
                CommonUtil.copyMsg(this,tvExpress.getText().toString());
                showShortToast(R.string.copy_success);
                break;
        }
    }
}
