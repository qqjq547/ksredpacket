package com.guochuang.mimedia.ui.activity.user;


import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.KsbTranAaaPresenter;
import com.guochuang.mimedia.mvp.view.KsbTranAaaView;
import com.guochuang.mimedia.tools.CashierInputFilter;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    PassDialog passDialog;
    int amount=0;

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
//        InputFilter[] filters={new CashierInputFilter()};
//        etTransKsb.setFilters(filters);
        etTransKsb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (TextUtils.isEmpty(charSequence)){
//                    tvMinerFee.setText("0");
//                }else {
//                    if (payeeUser!=null){
//                        double input= CommonUtil.formatDouble(Double.parseDouble(charSequence.toString()));
//                        tvMinerFee.setText(CommonUtil.formatDoubleStr(DoubleUtil.divide(input,rate)));
//                    }
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
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
                    showShortToast(R.string.pls_set_amount);
                    return;
                }
                amount=Integer.parseInt(amountStr);
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
//                            mvpPresenter.payMoney(
//                                    Constant.CHANNEL_CODE_ANDROID,
//                                    App.getInstance().getUserInfo().getUserAccountUuid(),
//                                    payeeUser.getUserAccountUuid(),
//                                    coinStr,
//                                    code,
//                                    remark
//                            );

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
