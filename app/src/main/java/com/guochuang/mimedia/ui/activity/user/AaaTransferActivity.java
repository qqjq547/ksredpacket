package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_tip)
    TextView tvTip;

    PassDialog passDialog;
    int amount = 0;
    DigitalIntCal intCal;
    ExchangeConfig exchangeConfig;
    String address;
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
        address=getIntent().getStringExtra(Constant.ADDRESS);
        if (!TextUtils.isEmpty(address)){
            etTransAddress.setText(address);
        }
        etTransCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    tvMinerFee.setText(CommonUtil.formatDouble(0,6));
                } else {
                    double input = CommonUtil.formatDouble(Double.parseDouble(charSequence.toString().trim()));
                    if (intCal != null&&exchangeConfig!=null) {
                        double equalAaa = DoubleUtil.mul(input, exchangeConfig.getWithdrawAAA().getServiceRate());
                        tvMinerFee.setText(CommonUtil.formatDouble(equalAaa,6));
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
                address=etTransAddress.getText().toString().trim();
                String amountStr = etTransCount.getText().toString().trim();
                if (TextUtils.isEmpty(address)){
                    showShortToast(R.string.aaa_address_empty);
                    return;
                }
                if (TextUtils.isEmpty(amountStr)){
                    showShortToast(R.string.pls_input_aaa_amount);
                    return;
                }
                try {
                    amount = Integer.parseInt(amountStr);
                }catch (Exception e){
                    showShortToast(R.string.ksb2aaa_tip_str);
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
                View contentView=LayoutInflater.from(this).inflate(R.layout.layout_aaa_transfer_notice,null);
                TextView tvAddress= ButterKnife.findById(contentView,R.id.tv_address);
                TextView tvCount= ButterKnife.findById(contentView,R.id.tv_count);
                tvAddress.setText(address);
                tvCount.setText(amountStr);
                new DialogBuilder(this)
                        .setTitle(R.string.transfer_ensure)
                        .setCustomView(contentView)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.valide_trade_code, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (passDialog == null) {
                                    passDialog = new PassDialog(AaaTransferActivity.this, new PassDialog.OnPassDialogListener() {
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
                            }
                        }).create().show();
                break;
        }
    }

    @Override
    public void setConfig(ExchangeConfig data) {
        if (data != null) {
            exchangeConfig = data;
            tvTip.setText(exchangeConfig.getWithdrawAAA().getTips());
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
        if (passDialog != null && passDialog.isShowing()) {
            passDialog.dismiss();
        }
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_AAA));
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
