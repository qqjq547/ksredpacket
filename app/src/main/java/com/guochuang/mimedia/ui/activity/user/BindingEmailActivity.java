package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Email;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.BindingEmailPresenter;
import com.guochuang.mimedia.mvp.view.BindingEmailView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GeneralUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class BindingEmailActivity extends MvpActivity<BindingEmailPresenter> implements BindingEmailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.et_binding_email)
    EditText etBindingEmail;
    @BindView(R.id.et_binding_email_verify)
    EditText etBindingEmailVerify;
    @BindView(R.id.ll_binding_email_verify)
    LinearLayout llBindingEmailVerify;
    @BindView(R.id.tv_binding_email_verify)
    TextView tvBindingEmailVerify;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.lin_password)
    LinearLayout linPassword;
    @BindView(R.id.tv_binding_email_confirm)
    TextView tvBindingEmailConfirm;

    @Override
    protected BindingEmailPresenter createPresenter() {
        return new BindingEmailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_bindingemail;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getString(R.string.title_email_binding));
    }


    @OnClick({R.id.iv_back, R.id.tv_binding_email_confirm, R.id.tv_binding_email_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_binding_email_verify:

                if (AntiShake.check(view.getId()))
                    return;
//                验证邮箱
                String emailStr = etBindingEmail.getText().toString().trim();
                if (!GeneralUtil.judgeEmailQual(emailStr)) {
                    showShortToast(R.string.input_email_tip);
                    return;
                }
                mvpPresenter.getEmailVerify(emailStr);
                break;
            case R.id.tv_binding_email_confirm:
                if (!doCheck()) return;

                String emailStr_ = etBindingEmail.getText().toString().trim();
                String VerifyCode = etBindingEmailVerify.getText().toString().trim();
//                String pwd = etPassword.getText().toString().trim();

                showLoadingDialog(null);

                mvpPresenter.applyEmail(emailStr_, VerifyCode, App.getInstance().getUserInfo().getUserAccountUuid());
                break;
        }
    }


    private void sendCode() {
        addSubscription(RxUtil.countdown(60)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvBindingEmailVerify.setEnabled(false);
                        tvBindingEmailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_gray);
                        tvBindingEmailVerify.setTextColor(getResources().getColor(R.color.text_white));
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        tvBindingEmailVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvBindingEmailVerify.setEnabled(true);
                        tvBindingEmailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvBindingEmailVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvBindingEmailVerify.setText(getString(R.string.btn_login_forget_verify));
                        tvBindingEmailVerify.setEnabled(true);
                        tvBindingEmailVerify.setBackgroundResource(R.drawable.bg_btn_forget_verify_red);
                        tvBindingEmailVerify.setTextColor(getResources().getColor(R.color.bg_btn_login_phone));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvBindingEmailVerify.setText(String.valueOf(integer));
                    }
                }));
    }

    @Override
    public void setData(String data) {
        showShortToast(getResources().getString(R.string.type_login_verify_send_success));
        sendCode();
    }

    @Override
    public void setError(String message) {
        closeLoadingDialog();
        showShortToast(message);

    }

    @Override
    public void setEmailVerifyError(String message) {
        //获取验证码失败
        showShortToast(message);
    }

    @Override
    public void setApplySuccess(Email data) {
        closeLoadingDialog();

        Intent intent = getIntent();
        intent.putExtra(Constant.EMAIL_KEY, data.getEmail());
        UserInfo userInfo=App.getInstance().getUserInfo();
        userInfo.setEmailAddress(data.getEmail());
        setResult(RESULT_OK,intent);
        finish();

    }

    private boolean doCheck() {
        String emailStr_ = etBindingEmail.getText().toString().trim();
        if (!GeneralUtil.judgeEmailQual(emailStr_)) {
            showShortToast(getResources().getString(R.string.input_email_error));
            return false;
        }
        if (etBindingEmailVerify.getText().length() < 1) {
            showShortToast(getResources().getString(R.string.input_verity_error));
            return false;
        }
//        if (etPassword.getText().length() < 6) {
//            showShortToast(getResources().getString(R.string.input_password_error));
//            return false;
//        }
        return true;
    }

}
