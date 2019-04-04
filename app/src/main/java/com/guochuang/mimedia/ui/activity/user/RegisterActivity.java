package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.GeneralUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.presenter.RegisterPresenter;
import com.guochuang.mimedia.mvp.view.RegisterView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_register_phone)
    EditText etPhone;
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
    @BindView(R.id.et_register_ima_verify)
    EditText etRegisterImaVerify;
    @BindView(R.id.iv_register_ima_verify)
    ImageView ivRegisterImaVerify;

    Captcha captcha;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initViewAndData() {
        GeneralUtil.phoneAddSpace(etPhone);
        mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
        tvTitle.setText(getString(R.string.title_login_register));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAgreenment.setText(Html.fromHtml(getString(R.string.content_login_agreenment), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvAgreenment.setText(Html.fromHtml(getResources().getString(R.string.content_login_agreenment)));
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

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
//        Toast.makeText(this, getResources().getString(R.string.type_login_register_success), Toast.LENGTH_SHORT).show();
//        PrefUtil.getInstance().setString(PrefUtil.USER_TOKEN, redbagDetail.getToken());
//        IntentUtils.startMainActivity(this);


        Intent intent = new Intent(this,MyWechatActivity.class).putExtra(Constant.WHO_OPEN_MYWECHATACTIVITY,getClass().getSimpleName());
        startActivity(intent);
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
        GlideImgManager.loadCornerImage(this, data.getUrl(), ivRegisterImaVerify, 8);
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

    @OnClick({R.id.iv_back,
            R.id.tv_register_verify,
            R.id.tv_register_agreenment,
            R.id.iv_register_ima_verify,
            R.id.tv_register_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_register_verify:
                if (AntiShake.check(view.getId()))
                    return;
                if (etRegisterImaVerify.getText().length() < 1) {
                    showShortToast(getResources().getString(R.string.input_verity_ima_error));
                } else {
                    mvpPresenter.getForgetSmsVerify(
                            GeneralUtil.removeAllSpace(etPhone.getText().toString()),
                            etRegisterImaVerify.getText().toString(),
                            captcha.getUuid()
                    );
                }
                break;
            case R.id.tv_register_agreenment:
                if (AntiShake.check(view.getId()))
                    return;
                IntentUtils.startWebActivity(this,getString(R.string.agree_rule),Constant.URL_AGREEMENT);
                break;
            case R.id.iv_register_ima_verify:
                if (AntiShake.check(view.getId()))
                    return;
                mvpPresenter.getRegisterImageVerify(Constant.REGISTER_CAPTCHA_IMA);
                break;
            case R.id.tv_register_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.getRegisterMobile(
                        Constant.TENANTCODE,
                        Constant.NATION_CODE,
                        GeneralUtil.removeAllSpace(etPhone.getText().toString()),
                        etVerify.getText().toString(),
                        etPassword.getText().toString(),
                        CommonUtil.getAppMetaData(this,"JPUSH_CHANNEL"),
                        "");
                break;
        }
    }

    private boolean doCheck() {
        if (etPhone.getText().length() < 13) {
            showShortToast(getResources().getString(R.string.input_phone_error));
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
