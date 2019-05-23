package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.presenter.BindingPhonePresenter;
import com.guochuang.mimedia.mvp.view.BindingPhoneView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;


import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class BindingPhoneAcitivity extends MvpActivity<BindingPhonePresenter> implements BindingPhoneView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_binding_phone_phone)
    EditText etPhone;
    @BindView(R.id.et_binding_phone_verify)
    EditText etVerify;
    @BindView(R.id.tv_binding_phone_verify)
    TextView tvVerify;
    @BindView(R.id.tv_binding_phone_confirm)
    TextView tvConfirm;
    @BindView(R.id.rl_ima_verify)
    RelativeLayout rlImaVerify;
    @BindView(R.id.et_binding_phone_ima_verify)
    EditText etBindingPhoneImaVerify;
    @BindView(R.id.iv_binding_phone_ima_verify)
    ImageView ivBindingPhoneImaVerify;
    @BindView(R.id.lin_password)
    LinearLayout linPassword;
    @BindView(R.id.et_password)
    EditText etPassword;

    String mobile;
    String smsCode;
    String password;
    String userAccountUuid;

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
        mvpPresenter.captchaIsEnabled();
        if (App.getInstance().getUserInfo()==null){
            //用户未登录，说明是微信登录，没有绑定手机号
            UserLogin userLogin = GsonUtil.GsonToBean(CommonUtil.baseDecrypt(getPref().getUserToken().split("\\.")[1]), UserLogin.class);
            userAccountUuid=userLogin.getSub();
            mvpPresenter.userBindMobileCaptcha(Constant.BIND_PHONE_CAPTCHA_IMA);
        }else {
            //登录后才绑定手机
            userAccountUuid=App.getInstance().getUserInfo().getUserAccountUuid();
        }
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
            R.id.tv_binding_phone_verify,
            R.id.iv_binding_phone_ima_verify,
            R.id.tv_binding_phone_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_binding_phone_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mobile=etPhone.getText().toString().trim();
                if (rlImaVerify.getVisibility() == View.VISIBLE && etBindingPhoneImaVerify.getText().length() < 1) {
                    showShortToast(getString(R.string.input_verity_ima_error));
                } else {
                    mvpPresenter.mobileExisted(mobile);
                }
                break;
            case R.id.iv_binding_phone_ima_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mvpPresenter.userBindMobileCaptcha(Constant.BIND_PHONE_CAPTCHA_IMA);
                break;
            case R.id.tv_binding_phone_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                if (App.getInstance().getUserInfo() == null) {
                    mvpPresenter.userBindPhone(
                            mobile,
                            smsCode,
                            userAccountUuid,
                            password);
                } else {
                    mvpPresenter.userSafeBindPhone(
                            mobile,
                            smsCode,
                            userAccountUuid);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //微信登录,后退就清除token信息，回到登录页面
        if (App.getInstance().getUserInfo()==null){
            getPref().setString(PrefUtil.USER_TOKEN,"");
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public void setData(BindingPhone data) {
        closeLoadingDialog();
        if (App.getInstance().getUserInfo()==null){//微信登录绑定手机号码,直接进入主界面
            getPref().setString(PrefUtil.MOBILE,data.getMobile());
            showShortToast(getResources().getString(R.string.bind_success));
            finish();
            IntentUtils.startMainActivity(this,true);
        }else {
            //登录后绑定手机号码
            Intent intent = getIntent();
            intent.putExtra(Constant.PHONE_KEY, data.getMobile());
            UserInfo userInfo=App.getInstance().getUserInfo();
            userInfo.setMobile(data.getMobile());
            App.getInstance().setUserInfo(userInfo);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void setSafeData(BindingPhone data) {
        closeLoadingDialog();
        getPref().setString(PrefUtil.MOBILE,mobile);
        showShortToast(getResources().getString(R.string.bind_success));
        finish();
        IntentUtils.startMainActivity(this,true);
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setCaptchaData(Captcha data) {
        GlideImgManager.loadCornerImage(this, data.getUrl(), ivBindingPhoneImaVerify, 8);
    }

    @Override
    public void setCaptchaError(String msg) {
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

    @Override
    public void setCaptchaIsEnabled(Boolean data) {
        if (data!=null){
            rlImaVerify.setVisibility(data.booleanValue()?View.VISIBLE:View.GONE);
        }
    }


    /**
     * 手机号是否存在
     * @param data
     */
    @Override
    public void mobileExisted(Integer data) {
        if (data!=null) {
            if (data.intValue()==0) {
                //不存在
                if (App.getInstance().getUserInfo()==null){
                    linPassword.setVisibility(View.VISIBLE);
                }
                mvpPresenter.userSendSms(
                        etPhone.getText().toString(),
                        etBindingPhoneImaVerify.getText().toString(),
                        userAccountUuid
                );
            } else if(data.intValue()==1){
                //已存在,已绑定
                linPassword.setVisibility(View.GONE);
                showShortToast(R.string.mobile_has_bind);
            }else if(data.intValue()==2){
                //已存在,未绑定
                linPassword.setVisibility(View.GONE);
                mvpPresenter.userSendSms(
                        etPhone.getText().toString(),
                        etBindingPhoneImaVerify.getText().toString(),
                        userAccountUuid
                );
            }
        }
    }

    private boolean doCheck() {
        mobile=etPhone.getText().toString().trim();
        smsCode=etVerify.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        if (mobile.length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        if (smsCode.length() < 1) {
            showShortToast(getResources().getString(R.string.input_verity_error));
            return false;
        }
        if (linPassword.getVisibility()==View.VISIBLE&&password.length() < 6) {
            showShortToast(getResources().getString(R.string.input_password_error));
            return false;
        }
        return true;
    }

}
