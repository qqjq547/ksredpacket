package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.KsbTrend;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.presenter.MyksbPresenter;
import com.guochuang.mimedia.mvp.view.MyksbView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyQCActivity extends MvpActivity<MyksbPresenter> implements MyksbView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_ksb_count)
    TextView tvKsbCount;
    @BindView(R.id.tv_share_benefit)
    TextView tvShareBenefit;
    @BindView(R.id.tv_equal_count)
    TextView tvEqualCount;
    @BindView(R.id.btn_transfer_sell)
    Button btnTransferAaa;
    @BindView(R.id.tv_qc_price)
    TextView mTvQcPrice;
    @BindView(R.id.wv_desp)
    WebView wvDesp;


    @Override
    protected MyksbPresenter createPresenter() {
        return new MyksbPresenter(this);
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
        wvDesp.loadUrl(Constant.URL_RULE_QC);
        mvpPresenter.getMyKsb();
//        mvpPresenter.getKsbTrend();
//        if (getPref().getBoolean(PrefUtil.AAA_SWITCH,false)){
//            btnTransferAaa.setVisibility(View.VISIBLE);
//        }else {
//            btnTransferAaa.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.lin_ksb, R.id.lin_share_benefit, R.id.lin_equal,
            R.id.btn_transfer_sell,R.id.btn_ksb_detail})
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
            case R.id.lin_share_benefit:
                startActivity(new Intent(this, ShareBenefitDetailActivity.class));
                break;
            case R.id.lin_equal:
                break;
            case R.id.btn_transfer_sell:
                if (getPref().getInt(PrefUtil.IDENTITY,0)==0) {
                    showShortToast(R.string.pls_identity_first);
                    startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                }else if(getPref().getInt(PrefUtil.SAFECODE,0)==0){
                    showShortToast(R.string.pls_safecode_first);
                    startActivityForResult(new Intent(this, TradePwdActivity.class), Constant.REFRESH);
                }else {

                    startActivityForResult(new Intent(this, QCTranSEALctivity.class),Constant.REQUEST_GRANT);
                }
                break;

            case R.id.btn_ksb_detail:
                //原ksb 明细
                startActivity(new Intent(this, MyKsbDetailsActivity.class));
                break;

        }
    }




    @Override
    public void setData(MyKsb data) {
        if (data!=null){
            tvKsbCount.setText(data.getCoin());
            tvShareBenefit.setText(data.getKsbPrice());
            tvEqualCount.setText(data.getMoney());
            mTvQcPrice.setText(data.getKsbPrice());
        }
    }

    @Override
    public void setKsbTrend(List<KsbTrend> data) {
    }

    @Override
    public void setMyAaa(MyAAA data) {
        closeLoadingDialog();
        if (data!=null){
            startActivityForResult(new Intent(this, MyKsbGrantActivity.class),Constant.REQUEST_GRANT);
        }
    }

    @Override
    public void setError(String msg) {
       showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.REQUEST_TRANSFER:
                case Constant.REQUEST_GRANT:
                    mvpPresenter.getMyKsb();
                    break;

            }
        }
    }
}
