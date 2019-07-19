package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.MyQC;
import com.guochuang.mimedia.mvp.presenter.MyQCPresenter;
import com.guochuang.mimedia.mvp.view.MyQCView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.UrlConfig;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyQCActivity extends MvpActivity<MyQCPresenter> implements MyQCView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_ksb_count)
    TextView tvKsbCount;
    @BindView(R.id.tv_equal_count)
    TextView tvEqualCount;
    @BindView(R.id.btn_transfer_sell)
    Button btnTransferseal;

    @BindView(R.id.tv_qc_price)
    TextView mTvQcPrice;
    @BindView(R.id.wv_desp)
    WebView wvDesp;


    @Override
    protected MyQCPresenter createPresenter() {
        return new MyQCPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_qc;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.my_qc_title);
        tvText.setText(R.string.detail);
        CommonUtil.initH5WebView(this, wvDesp);
        wvDesp.loadUrl(CommonUtil.getTimeStampUrl(UrlConfig.getHtmlUrl(UrlConfig.URL_RULE_QC)));
        showLoadingDialog(null);
        mvpPresenter.getMyQC();
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.lin_ksb, R.id.lin_equal,
            R.id.btn_transfer_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivity(new Intent(this, MyQCDetailsActivity.class));
                break;
            case R.id.lin_ksb:
                break;
            case R.id.lin_equal:
                break;
            case R.id.btn_transfer_sell:
                startActivityForResult(new Intent(this, QcTransferActivity.class),Constant.REQUEST_GRANT);
                break;

        }
    }


    @Override
    public void setMyQC(MyQC data) {
        closeLoadingDialog();
        if (data!=null){
            tvKsbCount.setText(data.getCoin());
            tvEqualCount.setText(data.getMoney());
            mTvQcPrice.setText(data.getQcMoney());
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
       showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.REQUEST_TRANSFER:
                case Constant.REQUEST_GRANT:
                    mvpPresenter.getMyQC();
                    break;

            }
        }
    }
}
