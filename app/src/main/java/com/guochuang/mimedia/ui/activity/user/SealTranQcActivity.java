package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.presenter.AaaTranKsbPresenter;
import com.guochuang.mimedia.mvp.presenter.SealTranQcPresenter;
import com.guochuang.mimedia.mvp.view.AaaTranKsbView;
import com.guochuang.mimedia.mvp.view.SealTranQcView;
import com.guochuang.mimedia.tools.CashierInputFilter;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SealTranQcActivity extends MvpActivity<SealTranQcPresenter> implements SealTranQcView {
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
    @BindView(R.id.tv_aaa_price)
    TextView tvAaaPrice;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    PassDialog passDialog;
    double amount=0;
    DigitalIntCal intCal;
    ExchangeConfig exchangeConfig;

    @Override
    protected SealTranQcPresenter createPresenter() {
        return new SealTranQcPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_seal_tran_qc;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.seal_trans_qc_title);
        InputFilter[] filters={new CashierInputFilter(4)};
        etTransKsb.setFilters(filters);
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
        mvpPresenter.intCal(Constant.DIGITAL_CURRENCY_SEAL,Constant.INT_CAL_SEAL_TO_QC);
        mvpPresenter.getExchangeConfig(Constant.DIGITAL_CURRENCY_SEAL);

    }

    @OnClick({R.id.iv_back, R.id.tv_all,R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_all:
                if (intCal!=null) {
                    etTransKsb.setText(String.valueOf(intCal.getDigitalCoin()));
                }
                break;
            case R.id.tv_confirm:
                String amountStr=etTransKsb.getText().toString().trim();
                if (TextUtils.isEmpty(amountStr)){
                    showShortToast(R.string.pls_input_seal_amount);
                    return;
                }
                try {
                    amount=Double.parseDouble(amountStr);
                }catch (Exception e){
                    showShortToast(getString(R.string.ksb2aaa_tip_str));
                    return;
                }

                if (intCal!=null&&amount>Double.parseDouble(intCal.getDigitalCoin())){
                    showShortToast(R.string.digital_seal_not_enouth);
                    return;
                }
                if (exchangeConfig!=null&&amount<exchangeConfig.getAaa2ksb().getMinLimit()){
                    showShortToast(String.format(getString(R.string.format_min_seal),exchangeConfig.getAaa2ksb().getMinLimit()));
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
                                    Constant.DIGITAL_CURRENCY_SEAL,
                                    Constant.DIGITAL_CURRENCY_SEAL,
                                    Constant.DIGITAL_CURRENCY_QC,
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
            tvAaaPrice.setText(String.valueOf(data.getDigitalRate()));
        }
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        //g广播通知aaa改变
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
        showShortToast(R.string.seal_to_qc_success);
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_QC));
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