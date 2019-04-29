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
import com.guochuang.mimedia.mvp.presenter.AaaTranKsbPresenter;
import com.guochuang.mimedia.mvp.view.AaaTranKsbView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AaaTranKsbActivity extends MvpActivity<AaaTranKsbPresenter> implements AaaTranKsbView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_aaa_num)
    TextView tvAaaNum;
    @BindView(R.id.tv_equal_ksb)
    TextView tvEqualKsb;
    @BindView(R.id.et_trans_ksb)
    EditText etTransKsb;
    @BindView(R.id.tv_arrive_ksb)
    TextView tvArriveKsb;
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
    DigitalIntCal intCal;
    ExchangeConfig exchangeConfig;

    @Override
    protected AaaTranKsbPresenter createPresenter() {
        return new AaaTranKsbPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_aaa_tran_ksb;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.aaa_trans_ksb);
        etTransKsb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)){
                    tvArriveKsb.setText("0");
                }else {
                    double input= CommonUtil.formatDouble(Double.parseDouble(charSequence.toString().trim()));
                        if (intCal!=null){
                            double equalMoney=DoubleUtil.mul(input,Double.parseDouble(intCal.getDigitalRate()));
                            double equalKsb=DoubleUtil.divide(equalMoney,Double.parseDouble(intCal.getKsbRate()));
                            tvArriveKsb.setText(CommonUtil.formatDoubleStr(equalKsb));
                        }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showLoadingDialog(null);
        mvpPresenter.intCal(Constant.INT_CAL_AAA_TO_KSB);
        mvpPresenter.getExchangeConfig();

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
                    showShortToast(R.string.pls_input_aaa_amount);
                    return;
                }
                amount=Integer.parseInt(amountStr);
                if (intCal!=null&&amount>(int)Double.parseDouble(intCal.getDigitalCoin())){
                    showShortToast(R.string.digital_not_enouth);
                    return;
                }
                if (exchangeConfig!=null&&amount<exchangeConfig.getAaa2ksb().getMinLimit()){
                    showShortToast(String.format(getString(R.string.format_min_aaa_to_ksb),exchangeConfig.getAaa2ksb().getMinLimit()));
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
                                    Constant.DIGITAL_CURRENCY_AAA,
                                    Constant.DIGITAL_CURRENCY_KSB,
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

    @Override
    public void setConfig(ExchangeConfig data) {
        if (data!=null){
            exchangeConfig=data;
            tvTip.setText(exchangeConfig.getAaa2ksb().getTips());
        }
    }

    @Override
    public void setIntCal(DigitalIntCal data) {
        closeLoadingDialog();
        if (data!=null){
            intCal=data;
            tvAaaNum.setText(String.valueOf(data.getDigitalCoin()));
            tvEqualKsb.setText(String.valueOf(data.getKsbCoin()));
            tvKsbPrice.setText(String.valueOf(data.getKsbRate()));
            tvAaaPrice.setText(String.valueOf(data.getDigitalRate()));
        }
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        //g广播通知aaa改变
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_AAA));
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
        showShortToast(R.string.aaa_to_ksb_success);
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_AAA));
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
}
