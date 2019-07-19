package com.guochuang.mimedia.ui.fragment;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.presenter.RegisterPresenter;
import com.guochuang.mimedia.mvp.view.RegisterView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GeneralUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.UrlConfig;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.user.RegisterActivity;
import com.guochuang.mimedia.ui.dialog.SlideVerifyDialog;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class RegistEmailFragment extends MvpFragment<RegisterPresenter> implements RegisterView {

    @BindView(R.id.et_email)
    EditText etMail;
    @BindView(R.id.et_register_verify)
    EditText etVerify;
    @BindView(R.id.tv_register_verify)
    TextView tvVerify;
    @BindView(R.id.et_register_password)
    EditText etPassword;
    @BindView(R.id.tv_register_agreenment)
    TextView tvAgreenment;
    @BindView(R.id.tv_register_confirm)
    TextView tvConfirm;
    @BindView(R.id.rl_ima_verify)
    RelativeLayout rlImaVerify;
    @BindView(R.id.et_register_ima_verify)
    EditText etRegisterImaVerify;
    @BindView(R.id.ll_register_ima_verify)
    LinearLayout llRegisterImaVerify;
    @BindView(R.id.iv_register_ima_verify)
    ImageView ivRegisterImaVerify;

    Captcha captcha;
    String email;
    String password;
    String imaVerify;
    String verify;
    int captchaType=0;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_regist_email;
    }

    @Override
    public void initViewAndData() {
        mvpPresenter.getCaptchaType();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAgreenment.setText(Html.fromHtml(getString(R.string.content_login_agreenment), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvAgreenment.setText(Html.fromHtml(getString(R.string.content_login_agreenment)));
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
                }));
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.type_login_register_success);
        ((RegisterActivity) getActivity()).login(email, password);
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setVerifyData(Captcha data) {
        this.captcha = data;
        GlideImgManager.loadCornerImage(getActivity(), data.getUrl(), ivRegisterImaVerify, 8);
    }

    @Override
    public void setVerifyError(String msg) {
        showShortToast(msg);
    }

    @Override
    public void setSmsData(String data) {
        showShortToast(R.string.type_login_verify_send_success);
        sendCode();
    }

    @Override
    public void setSmsError(String msg) {
        showShortToast(msg);
    }

    @Override
    public void setCaptchaType(Integer data) {
        //0   无验证码  1 图形验证码 2 滑块验证码 3 文字点选
        if (data!=null){
            captchaType=data.intValue();
            switch (captchaType){
                case 0:
                    rlImaVerify.setVisibility(View.GONE);
                    break;
                case 1:
                    rlImaVerify.setVisibility(View.VISIBLE);
                    mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
                    break;
                case 2:
                    rlImaVerify.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void setCaptchaTypeError(String msg) {
        showShortToast(msg);
    }

    @OnClick({
            R.id.tv_register_verify,
            R.id.iv_register_ima_verify,
            R.id.tv_register_agreenment,
            R.id.tv_register_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_verify:
                if (AntiShake.check(view.getId()))
                    return;
                email = etMail.getText().toString().trim();
                imaVerify=etRegisterImaVerify.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    showShortToast(R.string.pls_input_email);
                    return;
                } else if (!CommonUtil.isEmail(email)) {
                    showShortToast(R.string.email_format_error);
                    return;
                }
                switch (captchaType){
                    case 0:
                        mvpPresenter.getEmailVerify(
                                email,
                                "",
                                ""
                        );
                        break;
                    case 1:
                        if (captcha==null){
                            showShortToast(R.string.verity_ima_empty);
                        }else {
                            if (TextUtils.isEmpty(imaVerify)) {
                                showShortToast(R.string.input_verity_ima_error);
                            } else {
                                mvpPresenter.getEmailVerify(
                                        email,
                                        imaVerify,
                                        captcha.getUuid()
                                );
                            }
                        }
                        break;
                    case 2:
                            new SlideVerifyDialog(getActivity()) {

                                @Override
                                public void onResult(String flag) {
                                    mvpPresenter.getEmailVerify(
                                            email,
                                            "",
                                            flag
                                    );
                                }
                            }.show();
                        break;
                }
                break;
            case R.id.iv_register_ima_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
                break;
            case R.id.tv_register_agreenment:
                if (AntiShake.check(view.getId()))
                    return;
                IntentUtils.startWebActivity(getActivity(), null, UrlConfig.getHtmlUrl(UrlConfig.URL_REGIST_AGREEMENT));
                break;
            case R.id.tv_register_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.getRegisterEmail(
                        Constant.TENANTCODE,
                        Constant.NATION_CODE,
                        email,
                        verify,
                        password,
                        CommonUtil.getAppMetaData(getActivity(), "JPUSH_CHANNEL"),
                        "");
                break;
        }
    }

    private boolean doCheck() {
        email = etMail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        verify=etVerify.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            showShortToast(R.string.pls_input_email);
            return false;
        } else if (!CommonUtil.isEmail(email)) {
            showShortToast(R.string.email_format_error);
            return false;
        } else if (rlImaVerify.getVisibility()==View.VISIBLE&&TextUtils.isEmpty(verify)) {
            showShortToast(R.string.input_verity_error);
            return false;
        }
        if (password.length() < 6) {
            showShortToast(R.string.input_password_error);
            return false;
        }
        return true;
    }

}
