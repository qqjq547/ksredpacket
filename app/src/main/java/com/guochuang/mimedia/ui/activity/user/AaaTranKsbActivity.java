package com.guochuang.mimedia.ui.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.AaaTranKsbPresenter;
import com.guochuang.mimedia.mvp.view.AaaTranKsbView;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_arrive_aaa)
    TextView tvArriveAaa;
    @BindView(R.id.tv_ksb_price)
    TextView tvKsbPrice;
    @BindView(R.id.tv_aaa_price)
    TextView tvAaaPrice;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    PassDialog passDialog;
    int amount;

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

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
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
}
