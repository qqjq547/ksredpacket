package com.guochuang.mimedia.ui.activity.user;


import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.presenter.KsbTranAaaPresenter;
import com.guochuang.mimedia.mvp.view.KsbTranAaaView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;
import butterknife.BindView;
import butterknife.OnClick;


public class KsbTranAaactivity extends MvpActivity<KsbTranAaaPresenter> implements KsbTranAaaView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ksb_num)
    TextView tvKsbNum;
    @BindView(R.id.tv_equal_aaa)
    TextView tvEqualAaa;
    @BindView(R.id.et_trans_ksb)
    EditText etTransKsb;
    @BindView(R.id.tv_miner_fee)
    TextView tvMinerFee;
    @BindView(R.id.tv_arrive_aaa)
    TextView tvArriveAaa;
    @BindView(R.id.tv_ksb_price)
    TextView tvKsbPrice;
    @BindView(R.id.tv_aaa_price)
    TextView tvAaaPrice;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_tip)
    TextView tvTip;


    PassDialog passDialog;
    int amount=0;
    ExchangeConfig exchangeConfig;
    DigitalIntCal intCal;

    @Override
    protected KsbTranAaaPresenter createPresenter() {
        return new KsbTranAaaPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ksb_tran_aaa;
    }

    @Override
    public void initViewAndData() {
       tvTitle.setText(R.string.ksb_trans_aaa);
       etTransKsb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)){
                    tvMinerFee.setText("0");
                    tvArriveAaa.setText(CommonUtil.formatDouble(0,4));
                }else {
                    double input= CommonUtil.formatDouble(Double.parseDouble(charSequence.toString()));
                    if (exchangeConfig!=null){
                        tvMinerFee.setText(CommonUtil.formatDoubleStr(DoubleUtil.mul(input,exchangeConfig.getKsb2aaa().getServiceRate())));
                        if (intCal!=null){
                            double transKsb=input*(1-exchangeConfig.getKsb2aaa().getServiceRate());
                            double equalMoney=DoubleUtil.mul(transKsb,Double.parseDouble(intCal.getKsbRate()));
                            double equalAaa=DoubleUtil.divide(equalMoney,Double.parseDouble(intCal.getDigitalRate()));
                            tvArriveAaa.setText(CommonUtil.formatDouble(equalAaa,4));
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showLoadingDialog(null);
        mvpPresenter.intCal(Constant.INT_CAL_KSB_TO_AAA);
        mvpPresenter.getExchangeConfig();
    }

    @Override
    public void setConfig(ExchangeConfig data) {
        closeLoadingDialog();
        if (data!=null){
            exchangeConfig=data;
            tvTip.setText(exchangeConfig.getKsb2aaa().getTips());
        }
    }


    @Override
    public void setIntCal(DigitalIntCal data) {
        if (data!=null){
            intCal=data;
            tvKsbNum.setText(String.valueOf(data.getKsbCoin()));
            tvEqualAaa.setText(String.valueOf(data.getDigitalCoin()));
            tvKsbPrice.setText(String.valueOf(data.getKsbRate()));
            tvAaaPrice.setText(String.valueOf(data.getDigitalRate()));
        }
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_AAA));
        showShortToast(R.string.ksb_to_aaa_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.clearCode();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
               String amountStr=etTransKsb.getText().toString().trim();
                if (TextUtils.isEmpty(amountStr)){
                    showShortToast(R.string.pls_input_ksb_amount);
                    return;
                }
                amount=Integer.parseInt(amountStr);
                if (intCal!=null&&amount>(int)Double.parseDouble(intCal.getKsbCoin())){
                   showShortToast(R.string.coin_not_enouth);
                    return;
                }
                if (exchangeConfig!=null&&amount<exchangeConfig.getKsb2aaa().getMinLimit()){
                    showShortToast(String.format(getString(R.string.format_min_ksb_to_aaa),exchangeConfig.getKsb2aaa().getMinLimit()));
                    return;
                }
                if (passDialog == null) {
                    passDialog = new PassDialog(this, new PassDialog.OnPassDialogListener() {
                        @Override
                        public void close() {

                        }

                        @Override
                        public void go() {

                        }

                        @Override
                        public void onNumFull(String code) {
                            showLoadingDialog(null);
                            mvpPresenter.exchange(
                                    Constant.DIGITAL_CURRENCY_KSB,
                                    Constant.DIGITAL_CURRENCY_AAA,
                                    amount,
                                    code);

                        }
                    });
                    passDialog.setNeedIdentity(false);
                    passDialog.setBackVisible(false);
                }
                if (passDialog.isShowing()) {
                    passDialog.dismiss();
                } else {
                    passDialog.show();
                }
                break;
        }
    }
}
