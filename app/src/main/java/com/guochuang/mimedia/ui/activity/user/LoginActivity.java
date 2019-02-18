package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guochuang.mimedia.tools.WxLogin;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.mvp.presenter.LoginPresenter;
import com.guochuang.mimedia.mvp.view.LoginView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.iv_login_header)
    ImageView ivHeader;
    @BindView(R.id.et_login_phone)
    EditText etPhone;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.tv_login_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_login_register)
    TextView tvRegister;
    @BindView(R.id.tv_login_forget)
    TextView tvForget;
    @BindView(R.id.iv_login_wx)
    ImageView ivWx;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initViewAndData() {
    }
    @OnClick({R.id.tv_login_confirm,
            R.id.tv_login_register,
            R.id.tv_login_forget,
            R.id.iv_login_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.getLogin(
                        etPhone.getText().toString(),
                        etPassword.getText().toString(),
                        Constant.CAPTCHA,
                        Constant.SYSTEM_CODE,
                        Constant.LOGIN_TYPE,
                        SystemUtil.getAPNType(),
                        SystemUtil.getDeviceId(),
                        SystemUtil.getAndroidId(),
                        SystemUtil.getSystemModel(),
                        SystemUtil.getSystemModel(),
                        SystemUtil.getDeviceBrand(),
                        SystemUtil.getSystemVersion(),
                        SystemUtil.getAppVersion(),
                        SystemUtil.getDeviceResolution(),
                        SystemUtil.getDeviceId(),
                        JPushInterface.getRegistrationID(LoginActivity.this)
                );
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_login_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.iv_login_wx:
                if (AntiShake.check(view.getId()))
                    return;
                WxLogin.getInstance().login(new WxLogin.OnResultListener() {
                    @Override
                    public void onResult(String wxCode, String errMsg) {
                        if (TextUtils.isEmpty(errMsg)){
                            showLoadingDialog(null);
                            mvpPresenter.appWechatLogin(Constant.TENANTCODE,
                                    Constant.SYSTEM_CODE,
                                    wxCode,
                                    Constant.LOGIN_TYPE,
                                    SystemUtil.getAPNType(),
                                    SystemUtil.getDeviceId(),
                                    SystemUtil.getAndroidId(),
                                    SystemUtil.getSystemModel(),
                                    SystemUtil.getSystemModel(),
                                    SystemUtil.getDeviceBrand(),
                                    SystemUtil.getSystemVersion(),
                                    SystemUtil.getAppVersion(),
                                    SystemUtil.getDeviceResolution(),
                                    SystemUtil.getDeviceId(),
                                    JPushInterface.getRegistrationID(LoginActivity.this));
                        }else {
                            showShortToast(errMsg);
                        }
                    }
                });
                break;
        }
    }

    private boolean doCheck() {
        if (etPhone.getText().length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        if (etPassword.getText().length() < 6) {
            showShortToast(getResources().getString(R.string.input_password_error));
            return false;
        }
        return true;
    }
    @Override
    public void setLoginData(String data) {
        closeLoadingDialog();
        UserLogin userLogin = new Gson().fromJson(CommonUtil.baseDecrypt(data.split("\\.")[1]), UserLogin.class);
        getPref().setString(PrefUtil.USER_TOKEN, data);
        if (TextUtils.isEmpty(userLogin.getMobile())) {
            Intent intent = new Intent(this, BindingPhoneAcitivity.class);
            startActivity(intent);
            finish();
        } else {
            getPref().setString(PrefUtil.MOBILE, userLogin.getMobile());
            IntentUtils.startMainActivity(this, true);
            finish();
        }
    }

    @Override
    public void setLoginError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
