package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.presenter.AaaTransferPresenter;
import com.guochuang.mimedia.mvp.view.AaaTransferView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AaaTransferActivity extends MvpActivity<AaaTransferPresenter> implements AaaTransferView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_aaa_num)
    TextView tvAaaNum;
    @BindView(R.id.et_trans_address)
    EditText etTransAddress;
    @BindView(R.id.et_trans_count)
    EditText etTransCount;
    @BindView(R.id.tv_miner_fee)
    TextView tvMinerFee;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    PassDialog passDialog;
    int amount = 0;
    DigitalIntCal intCal;
    ExchangeConfig exchangeConfig;

    @Override
    protected AaaTransferPresenter createPresenter() {
        return new AaaTransferPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_aaa_transfer;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.aaa_transfer_title);
        etTransCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    tvMinerFee.setText("0");
                } else {
                    double input = CommonUtil.formatDouble(Double.parseDouble(charSequence.toString().trim()));
                    if (intCal != null&&exchangeConfig!=null) {
                        double equalAaa = DoubleUtil.mul(input, exchangeConfig.getWithdrawAAA().getServiceRate());
                        tvMinerFee.setText(CommonUtil.formatDoubleStr(equalAaa));
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
                final String address=etTransAddress.getText().toString().trim();
                String amountStr = etTransCount.getText().toString().trim();
                amount = Integer.parseInt(amountStr);
                if (TextUtils.isEmpty(address)){
                    showShortToast(R.string.aaa_address_empty);
                    return;
                }
                if (intCal != null && amount > (int) Double.parseDouble(intCal.getDigitalCoin())) {
                    showShortToast(R.string.digital_not_enouth);
                    return;
                }
                if (exchangeConfig != null && amount < exchangeConfig.getWithdrawAAA().getMinLimit()) {
                    showShortToast(String.format(getString(R.string.format_min_aaa_to_ksb),exchangeConfig.getWithdrawAAA().getMinLimit()));
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
                            mvpPresenter.withdrawCoin(
                                    address,
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
        if (data != null) {
            exchangeConfig = data;
        }
    }

    @Override
    public void setIntCal(DigitalIntCal data) {
        closeLoadingDialog();
        if (data != null) {
            intCal = data;
            tvAaaNum.setText(String.valueOf(data.getDigitalCoin()));
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        //广播通通知我的中心页面刷新AAA
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_AAA));

        if (passDialog != null && passDialog.isShowing()) {
            passDialog.dismiss();
        }
        showShortToast(R.string.withdraw_coin_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        if (passDialog != null && passDialog.isShowing()) {
            passDialog.clearCode();
        }
    }

}
