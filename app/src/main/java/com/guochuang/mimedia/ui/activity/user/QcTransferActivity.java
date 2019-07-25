package com.guochuang.mimedia.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.presenter.SealTransferPresenter;
import com.guochuang.mimedia.mvp.view.SealTransferView;
import com.guochuang.mimedia.tools.CashierInputFilter;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.ui.activity.common.MyCaptureActivity;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class QcTransferActivity extends MvpActivity<SealTransferPresenter> implements SealTransferView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_qc_num)
    TextView tvQcNum;
    @BindView(R.id.tv_seal_num)
    TextView tvSealNum;
    @BindView(R.id.et_trans_address)
    EditText etTransAddress;
    @BindView(R.id.et_trans_identity)
    EditText etTransIdentity;
    @BindView(R.id.et_trans_count)
    EditText etTransCount;
    @BindView(R.id.et_verify)
    EditText etVerify;
    @BindView(R.id.tv_verify)
    TextView tvVerify;
    @BindView(R.id.tv_miner_fee)
    TextView tvMinerFee;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.rbtn_mobile)
    RadioButton rbtnMobile;
    @BindView(R.id.rbtn_email)
    RadioButton rbtnEmail;
    @BindView(R.id.tv_type_name)
    TextView tvTypeName;
    @BindView(R.id.tv_transter_seal_num)
    TextView tvTransterSealNum;
    @BindView(R.id.tv_arrive_num)
    TextView tvArriveNum;

    PassDialog passDialog;
    double amount = 0;
    DigitalIntCal intCal;
    ExchangeConfig exchangeConfig;
    String address;
    String identity;
    String mobile;
    String emailAddress;
    String uuid;
    String captcha;
    Subscription subscription;
    static final String TYPE_MOBILE = "1";
    static final String TYPE_EMAIL = "2";
    String curType = TYPE_MOBILE;

    @Override
    protected SealTransferPresenter createPresenter() {
        return new SealTransferPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_qc_transfer;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.qc_transfer_title);
        mobile = App.getInstance().getUserInfo().getMobile();
        emailAddress = App.getInstance().getUserInfo().getEmailAddress();
        uuid = App.getInstance().getUserInfo().getUserAccountUuid();
        address = getIntent().getStringExtra(Constant.ADDRESS);
        if (!TextUtils.isEmpty(address)) {
            etTransAddress.setText(address);
        }
        InputFilter[] filters = {new CashierInputFilter(4)};
        etTransCount.setFilters(filters);
        etTransCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    tvMinerFee.setText(CommonUtil.formatDouble(0, 4));
                    tvTransterSealNum.setText(CommonUtil.formatDouble(0,4));
                    tvArriveNum.setText(CommonUtil.formatDouble(0,4));
                } else {
                    if (intCal != null && exchangeConfig != null) {
                        double qcFee=Double.parseDouble(charSequence.toString().trim());
                        double rate=Double.parseDouble(intCal.getDigitalRate());
                        double input = CommonUtil.formatDouble(DoubleUtil.divide(qcFee,rate));
                        double mineFree = DoubleUtil.mul(input, exchangeConfig.getWithdrawQC().getServiceRate());
                        tvMinerFee.setText(CommonUtil.formatDouble(mineFree, 4));
                        tvTransterSealNum.setText(CommonUtil.formatDouble(input,4));
                        tvArriveNum.setText(CommonUtil.formatDouble(input-mineFree,4));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
                tvVerify.setText(getString(R.string.get_vertify_code));
                tvVerify.setEnabled(true);
                tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                tvVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                etVerify.setText(null);
                switch (i) {
                    case R.id.rbtn_mobile:
                        curType = TYPE_MOBILE;
                        break;
                    case R.id.rbtn_email:
                        curType = TYPE_EMAIL;
                        break;
                }
            }
        });
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(emailAddress)) {
            rgType.setVisibility(View.VISIBLE);
            tvTypeName.setText(R.string.verify_code);
            rbtnMobile.setChecked(true);
            curType = TYPE_MOBILE;
        } else {
            rgType.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mobile)) {
                tvTypeName.setHint(R.string.msg_vertify_code);
                curType = TYPE_MOBILE;
            } else {
                tvTypeName.setHint(R.string.email_verify_code);
                curType = TYPE_EMAIL;
            }
        }
        showLoadingDialog(null);
        mvpPresenter.intCal(Constant.DIGITAL_CURRENCY_SEAL, Constant.INT_CAL_QC_TO_SEAL);
        mvpPresenter.getExchangeConfig(Constant.DIGITAL_CURRENCY_QC);

    }

    @OnClick({R.id.iv_back, R.id.iv_scan, R.id.iv_menu, R.id.tv_verify, R.id.tv_all, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_scan:
                new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivityForResult(new Intent(QcTransferActivity.this, MyCaptureActivity.class), Constant.REQUEST_CAPTURE);
                        } else {
                            showShortToast(R.string.get_camera_permission);
                        }
                    }
                });
                break;
            case R.id.iv_menu:
                startActivityForResult(new Intent(this, CoinAddressActivity.class), Constant.REQUEST_PICK_COIN_ADDRESS);
                break;
            case R.id.tv_verify:
                if (AntiShake.check(view.getId()))
                    return;
                if (curType.equals(TYPE_MOBILE)) {
                    mvpPresenter.sendSmsCode(mobile, uuid);
                } else {
                    mvpPresenter.sendEmailCode(emailAddress, uuid);
                }

                break;
            case R.id.tv_all:
                if (intCal != null) {
                    etTransCount.setText(String.valueOf(intCal.getKsbCoin()));
                }
                break;
            case R.id.tv_confirm:
                address = etTransAddress.getText().toString().trim();
                identity = etTransIdentity.getText().toString().trim();
                String amountStr = etTransCount.getText().toString().trim();
                captcha = etVerify.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    showShortToast(R.string.seal_address_empty);
                    return;
                }
//                if (TextUtils.isEmpty(identity)){
//                    showShortToast(R.string.seal_transfer_identity_number);
//                    return;
//                }

                if (TextUtils.isEmpty(amountStr)) {
                    showShortToast(R.string.pls_input_qc_transfer_count);
                    return;
                }
                try {
                    amount = Double.parseDouble(amountStr);
                } catch (Exception e) {
                    showShortToast(R.string.coin_tip_str);
                    return;
                }

                if (intCal != null && amount > Double.parseDouble(intCal.getKsbCoin())) {
                    showShortToast(R.string.digital_qc_not_enouth);
                    return;
                }
                if (exchangeConfig != null && amount < exchangeConfig.getWithdrawQC().getMinLimit()) {
                    showShortToast(String.format(getString(R.string.format_min_qc), String.valueOf(exchangeConfig.getWithdrawQC().getMinLimit())));
                    return;
                }
                if (TextUtils.isEmpty(captcha)) {
                    showShortToast(R.string.pls_input_msg_vertify_code);
                    return;
                }
                View contentView = LayoutInflater.from(this).inflate(R.layout.layout_transfer_notice, null);
                TextView tvAddress = ButterKnife.findById(contentView, R.id.tv_address);
                TextView tvCount = ButterKnife.findById(contentView, R.id.tv_count);
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
                                    passDialog = new PassDialog(QcTransferActivity.this, new PassDialog.OnPassDialogListener() {
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
                                                    Constant.DIGITAL_CURRENCY_QC,
                                                    address,
                                                    amount,
                                                    code,
                                                    captcha,
                                                    curType,
                                                    identity
                                            );

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
            tvTip.setText(exchangeConfig.getWithdrawQC().getTips());
        }
    }

    @Override
    public void setIntCal(DigitalIntCal data) {
        closeLoadingDialog();
        if (data != null) {
            intCal = data;
            tvQcNum.setText(String.valueOf(data.getKsbCoin()));
            tvSealNum.setText(String.valueOf(data.getDigitalCoin()));
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        //广播通通知我的中心页面刷新AAA
        if (passDialog != null && passDialog.isShowing()) {
            passDialog.dismiss();
        }
        sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
        showShortToast(R.string.withdraw_coin_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setSmsCode(Boolean data) {
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendCode();
    }

    @Override
    public void setEmailCode(Boolean data) {
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendCode();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        if (passDialog != null && passDialog.isShowing()) {
            passDialog.clearCode();
        }
    }

    private void sendCode() {
        subscription = RxUtil.countdown(60)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvVerify.setEnabled(false);
                        tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_gray);
                        tvVerify.setTextColor(getResources().getColor(R.color.text_white));
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        tvVerify.setText(getString(R.string.btn_login_reset_verify));
                        tvVerify.setEnabled(true);
                        tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvVerify.setText(getString(R.string.btn_login_reset_verify));
                        tvVerify.setEnabled(true);
                        tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvVerify.setText(String.valueOf(integer));
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CAPTURE:
                    String address = data.getStringExtra(Constant.ADDRESS);
                    etTransAddress.setText(address);
                    break;
                case Constant.REQUEST_PICK_COIN_ADDRESS:
                    String coinAddress = data.getStringExtra(Constant.COIN_ADDRESS);
                    etTransAddress.setText(coinAddress);
                    break;
            }
        }
    }

}
