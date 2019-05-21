package com.guochuang.mimedia.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.presenter.ForgetPresenter;
import com.guochuang.mimedia.mvp.view.ForgetView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class ForgetEmailFragment extends MvpFragment<ForgetPresenter> implements ForgetView {

    @BindView(R.id.et_login_forget_email)
    EditText etEmail;
    @BindView(R.id.et_login_forget_verify)
    EditText etVerify;
    @BindView(R.id.tv_login_forget_verify)
    TextView tvVerify;
    @BindView(R.id.et_login_forget_password)
    EditText etPassword;
    @BindView(R.id.tv_login_forget_confirm)
    TextView tvConfirm;

    String email;
    String password;

    @Override
    protected ForgetPresenter createPresenter() {
        return new ForgetPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_forget_email;
    }

    @Override
    public void initViewAndData() {
        mvpPresenter.getForgetImageVerify(Constant.FORGET_RESET_CAPTCHA_IMA);

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

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.type_login_foeget_success));
        getActivity().finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setVerifyData(Captcha data) {
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

    @OnClick({
            R.id.tv_login_forget_verify,
            R.id.tv_login_forget_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget_verify:
                if (AntiShake.check(view.getId()))
                    return;
                email=etEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    showShortToast(getResources().getString(R.string.pls_input_email));
                } else if (!CommonUtil.isEmail(email)) {
                showShortToast(getResources().getString(R.string.email_format_error));
                } else {
                    mvpPresenter.getForgetEmailVerify(
                            email
                    );
                }
                break;
            case R.id.tv_login_forget_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.getForget(
                        Constant.RESET_NATION_CODE,
                        email,
                        etVerify.getText().toString(),
                        password
                );
                break;
        }
    }

    private boolean doCheck() {
        email=etEmail.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            showShortToast(getResources().getString(R.string.pls_input_email));
            return false;
        }
        if (!CommonUtil.isEmail(email)) {
            showShortToast(getResources().getString(R.string.email_format_error));
            return false;
        }
        if (etVerify.getText().length() < 1) {
            showShortToast(getResources().getString(R.string.input_verity_error));
            return false;
        }
        if (etPassword.getText().length() < 6) {
            showShortToast(getResources().getString(R.string.input_password_error));
            return false;
        }
        return true;
    }
}
