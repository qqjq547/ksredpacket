package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.BindingPhonePresenter;
import com.guochuang.mimedia.mvp.view.BindingPhoneView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class BindingPhoneAcitivity extends MvpActivity<BindingPhonePresenter> implements BindingPhoneView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_email_verify)
    RelativeLayout rlEmailVerify;
    @BindView(R.id.et_binding_mail_verify)
    EditText etBindingMailVerify;
    @BindView(R.id.tv_binding_mail_verify)
    TextView tvBindingMailVerify;
    @BindView(R.id.et_binding_phone_phone)
    EditText etPhone;
    @BindView(R.id.et_binding_phone_verify)
    EditText etVerify;
    @BindView(R.id.tv_binding_phone_verify)
    TextView tvVerify;
    @BindView(R.id.tv_binding_phone_confirm)
    TextView tvConfirm;

    String mobile;
    String smsCode;
    String password;
    String userAccountUuid;
    String emailCode;


    @Override
    protected BindingPhonePresenter createPresenter() {
        return new BindingPhonePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getString(R.string.title_phone_binding));
            //登录后才绑定手机
        userAccountUuid = App.getInstance().getUserInfo().getUserAccountUuid();
    }
    private void sendEmailCode() {
        addSubscription(RxUtil.countdown(60)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvBindingMailVerify.setEnabled(false);
                        tvBindingMailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_gray);
                        tvBindingMailVerify.setTextColor(getResources().getColor(R.color.text_white));
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        tvBindingMailVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvBindingMailVerify.setEnabled(true);
                        tvBindingMailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvBindingMailVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvBindingMailVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvBindingMailVerify.setEnabled(true);
                        tvBindingMailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvBindingMailVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvBindingMailVerify.setText(String.valueOf(integer));
                    }
                }));
    }

    private void sendCode() {
        addSubscription(RxUtil.countdown(60)
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
                        tvVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvVerify.setEnabled(true);
                        tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvVerify.setEnabled(true);
                        tvVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvVerify.setText(String.valueOf(integer));
                    }
                }));
    }

    @OnClick({R.id.iv_back,
            R.id.tv_binding_mail_verify,
            R.id.tv_binding_phone_verify,
            R.id.tv_binding_phone_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_binding_mail_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mvpPresenter.getEmailVerify();
                break;
            case R.id.tv_binding_phone_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mobile = etPhone.getText().toString().trim();
                if (mobile.length() < 11) {
                    showShortToast(getResources().getString(R.string.input_phone_error));
                    return;
                }
                 mvpPresenter.getMobileVertify(
                                 mobile,
                                null,
                                userAccountUuid
                 );
                break;
            case R.id.tv_binding_phone_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.userSafeBindPhone(
                            mobile,
                            smsCode,
                            emailCode,
                            userAccountUuid);
                break;
        }
    }

    @Override
    public void setData(BindingPhone data) {
        closeLoadingDialog();
            //登录后绑定手机号码
            Intent intent = getIntent();
            intent.putExtra(Constant.PHONE_KEY, data.getMobile());
            UserInfo userInfo = App.getInstance().getUserInfo();
            userInfo.setMobile(data.getMobile());
            App.getInstance().setUserInfo(userInfo);
            setResult(RESULT_OK, intent);
            finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setEmailCaptchaData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendEmailCode();
    }

    @Override
    public void setEmailCaptchaError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
    @Override
    public void setSmsData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendCode();
    }

    @Override
    public void setSmsError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }


    private boolean doCheck() {
        emailCode = etBindingMailVerify.getText().toString().trim();
        mobile = etPhone.getText().toString().trim();
        smsCode = etVerify.getText().toString().trim();
        if (TextUtils.isEmpty(emailCode)) {
            showShortToast(getResources().getString(R.string.pls_iput_mail_verify));
            return false;
        }
        if (mobile.length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        if (TextUtils.isEmpty(smsCode)) {
            showShortToast(getResources().getString(R.string.pls_iput_mobile_verify));
            return false;
        }
        return true;
    }
}
