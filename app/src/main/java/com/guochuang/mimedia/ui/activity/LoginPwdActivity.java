package com.guochuang.mimedia.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.ForgetPresenter;
import com.guochuang.mimedia.mvp.view.ForgetView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;

public class LoginPwdActivity extends MvpActivity<ForgetPresenter> implements ForgetView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_image_vertify_code)
    EditText etImageVertifyCode;
    @BindView(R.id.iv_image_vertify_code)
    ImageView ivImageVertifyCode;
    @BindView(R.id.et_msg_vertify_code)
    EditText etMsgVertifyCode;
    @BindView(R.id.tv_get_vertify_code)
    TextView tvGetVertifyCode;
    @BindView(R.id.et_new_login_pwd)
    EditText etNewLoginPwd;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    Captcha captcha;
    UserInfo userInfo;

    @Override
    protected ForgetPresenter createPresenter() {
        return new ForgetPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login_pwd;
    }

    @Override
    public void initViewAndData() {
        mvpPresenter.getForgetImageVerify(Constant.FORGET_RESET_CAPTCHA_IMA);
        tvTitle.setText(R.string.login_pwd);
        userInfo = App.getInstance().getUserInfo();
        StringBuffer sb = new StringBuffer(userInfo.getMobile());
        sb.replace(3, 7, getResources().getString(R.string.star));
        tvMobile.setText(sb.toString());
    }


    @OnClick({R.id.iv_back, R.id.iv_image_vertify_code, R.id.tv_get_vertify_code, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image_vertify_code:
                mvpPresenter.getForgetImageVerify(Constant.FORGET_RESET_CAPTCHA_IMA);
                break;
            case R.id.tv_get_vertify_code:
                if (etImageVertifyCode.getText().length() < 1) {
                    showShortToast(getResources().getString(R.string.input_verity_ima_error));
                } else {
                    mvpPresenter.getForgetSmsVerify(
                            userInfo.getMobile(),
                            etImageVertifyCode.getText().toString(),
                            captcha.getUuid()
                    );
                }
                break;
            case R.id.tv_sure:
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.getForget(
                        Constant.RESET_NATION_CODE,
                        userInfo.getMobile(),
                        etMsgVertifyCode.getText().toString(),
                        etNewLoginPwd.getText().toString()
                );
                break;
        }
    }

    private void sendCode() {
        addSubscription(RxUtil.countdown(60)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvGetVertifyCode.setEnabled(false);
//                        tvGetVertifyCode.setBackgroundResource(R.drawable.bg_btn_forget_verify_gray);
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_gray));
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        tvGetVertifyCode.setText(getString(R.string.btn_login_forget_verify));
                        tvGetVertifyCode.setEnabled(true);
//                        tvGetVertifyCode.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_blue));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvGetVertifyCode.setText(getString(R.string.btn_login_forget_verify));
                        tvGetVertifyCode.setEnabled(true);
//                        tvGetVertifyCode.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_blue));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvGetVertifyCode.setText(String.valueOf(integer));
                    }
                }));
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.type_login_foeget_success));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setVerifyData(Captcha data) {
        this.captcha = data;
        GlideImgManager.loadCornerImage(this, data.getUrl(), ivImageVertifyCode, 8);
    }

    @Override
    public void setVerifyError(String msg) {
        showShortToast(msg);
    }

    @Override
    public void setSmsData(String data) {
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendCode();
    }

    @Override
    public void setSmsError(String msg) {
        showShortToast(msg);
    }

    private boolean doCheck() {
        if (etMsgVertifyCode.getText().length() < 1) {
            showShortToast(getResources().getString(R.string.input_verity_error));
            return false;
        }
        if (etNewLoginPwd.getText().length() < 6) {
            showShortToast(getResources().getString(R.string.input_password_error));
            return false;
        }
        return true;
    }
}
