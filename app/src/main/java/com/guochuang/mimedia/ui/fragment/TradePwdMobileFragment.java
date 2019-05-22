package com.guochuang.mimedia.ui.fragment;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.TradePwdPresenter;
import com.guochuang.mimedia.mvp.view.TradePawView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class TradePwdMobileFragment extends MvpFragment<TradePwdPresenter> implements TradePawView {
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
    @BindView(R.id.et_new_trade_pwd)
    EditText etNewTradePwd;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    UserInfo userInfo;
    Captcha captcha;

    @Override
    protected TradePwdPresenter createPresenter() {
        return new TradePwdPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_trade_pwd_mobile;
    }

    @Override
    public void initViewAndData() {
        mvpPresenter.userSafeCodeImageVerify(Constant.SAFE_RESET_CAPTCHA_IMA);
        userInfo = App.getInstance().getUserInfo();
        StringBuffer sb = new StringBuffer(userInfo.getMobile());
        sb.replace(3, 7, getResources().getString(R.string.star));
        tvMobile.setText(sb.toString());
    }


    @OnClick({R.id.iv_image_vertify_code, R.id.tv_get_vertify_code, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_image_vertify_code:
                mvpPresenter.userSafeCodeImageVerify(Constant.SAFE_RESET_CAPTCHA_IMA);
                break;
            case R.id.tv_get_vertify_code:
                if (TextUtils.isEmpty(etImageVertifyCode.getText())) {
                    showShortToast(getResources().getString(R.string.input_verity_ima_error));
                    return;
                }
                if (TextUtils.isEmpty(userInfo.getMobile())) {
                    showShortToast(getResources().getString(R.string.input_phone_error));
                    return;
                }
                mvpPresenter.userSendSms(
                        userInfo.getMobile(),
                        etImageVertifyCode.getText().toString(),
                        captcha.getUuid()
                );
                break;
            case R.id.tv_sure:
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.userSafeReset(
                        etMsgVertifyCode.getText().toString(),
                        etNewTradePwd.getText().toString()
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
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_gray));
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        tvGetVertifyCode.setText(getString(R.string.btn_login_forget_verify));
                        tvGetVertifyCode.setEnabled(true);
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_blue));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tvGetVertifyCode.setText(getString(R.string.btn_login_forget_verify));
                        tvGetVertifyCode.setEnabled(true);
                        tvGetVertifyCode.setTextColor(getResources().getColor(R.color.text_blue));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvGetVertifyCode.setText(String.valueOf(integer));
                    }
                }));
    }

    private boolean doCheck() {
        if (userInfo.getMobile().length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        if (etMsgVertifyCode.getText().length() < 1) {
            showShortToast(getResources().getString(R.string.input_verity_error));
            return false;
        }
        if (etNewTradePwd.getText().length() < 6) {
            showShortToast(getResources().getString(R.string.input_safe_password_error));
            return false;
        }
        return true;
    }

    @Override
    public void setData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.setup_success));
        getPref().setInt(PrefUtil.SAFECODE,1);
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setVerifyData(Captcha data) {
        captcha = data;
        GlideImgManager.loadCornerImage(getActivity(), data.getUrl(), ivImageVertifyCode, 8);
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
}
