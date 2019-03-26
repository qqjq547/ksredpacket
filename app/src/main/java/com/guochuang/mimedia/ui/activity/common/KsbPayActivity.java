package com.guochuang.mimedia.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PayeeUser;
import com.guochuang.mimedia.mvp.model.PaymentResult;
import com.guochuang.mimedia.mvp.presenter.KsbPayPresenter;
import com.guochuang.mimedia.mvp.view.KsbPayView;
import com.guochuang.mimedia.tools.CashierInputFilter;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DoubleUtil;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.tools.pay.PayResult;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.sz.gcyh.KSHongBao.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KsbPayActivity extends MvpActivity<KsbPayPresenter> implements KsbPayView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.tv_my_ksb)
    TextView tvMyKsb;
    @BindView(R.id.tv_my_money)
    TextView tvMyMoney;
    @BindView(R.id.lin_head)
    LinearLayout linHead;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_sure)
    Button btnSure;

    String userAccountUuid;
    PayeeUser payeeUser;
    PassDialog passDialog;
    double rate;
    @Override
    protected KsbPayPresenter createPresenter() {
        return new KsbPayPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ksb_pay;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.ksb_pay_title);
        userAccountUuid=getIntent().getStringExtra(Constant.USER_ACCOUNT_UUID);
        InputFilter[] filters={new CashierInputFilter()};
        etMoney.setFilters(filters);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)){
                    tvCoin.setText("0");
                }else {
                    if (payeeUser!=null){
                       double input= CommonUtil.formatDouble(Double.parseDouble(charSequence.toString()));
                        tvCoin.setText(CommonUtil.formatDoubleStr(DoubleUtil.divide(input,rate)));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showLoadingDialog(null);
        mvpPresenter.queryUserInfoByAccountUuid(userAccountUuid);
    }

    @OnClick({R.id.iv_back, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_sure:
                final String coinStr=tvCoin.getText().toString().trim();
                final String remark=etRemark.getText().toString().trim();
                if (TextUtils.isEmpty(coinStr)){
                    showShortToast(R.string.pls_set_money);
                    return;
                }
                double coin=Double.parseDouble(coinStr);
                double myCoin=Double.parseDouble(payeeUser.getCoin());
                if (coin>myCoin){
                    showShortToast(R.string.ksb_loss);
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
                            mvpPresenter.payMoney(
                                    Constant.CHANNEL_CODE_ANDROID,
                                    App.getInstance().getUserInfo().getUserAccountUuid(),
                                    userAccountUuid,
                                    coinStr,
                                    code,
                                    remark
                                    );

                        }
                    });
                    passDialog.setNeedIdentity(true);
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
    public void setData(PayeeUser data) {
      closeLoadingDialog();
      if (data!=null){
          payeeUser=data;
          rate=Double.parseDouble(payeeUser.getRate());
          GlideImgManager.loadCircleImage(this,data.getAvatar(),ivAvatar);
          tvNickname.setText(data.getNickName());
          tvRealname.setText(String.format(getString(R.string.format_kuahao),data.getRealName()));
          tvMyKsb.setText(data.getCoin());
          double coin=0;
          if (!TextUtils.isEmpty(data.getCoin())){
               coin=Double.parseDouble(data.getCoin());
          }
          tvMyMoney.setText(CommonUtil.formatDoubleStr(DoubleUtil.mul(coin,rate)));

      }
    }

    @Override
    public void setPayResult(PaymentResult data) {
        closeLoadingDialog();
        passDialog.dismiss();
        if (data!=null){
            startActivity(new Intent(this,PayResultActivity.class).putExtra(Constant.DATA,data));
            finish();
        }else {
            showShortToast(R.string.unknow_error_and_late);
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
