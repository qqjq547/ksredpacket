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
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class TradePwdEmailFragment extends MvpFragment<TradePwdPresenter> implements TradePawView {
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.et_msg_vertify_code)
    EditText etMsgVertifyCode;
    @BindView(R.id.tv_get_vertify_code)
    TextView tvGetVertifyCode;
    @BindView(R.id.et_new_trade_pwd)
    EditText etNewTradePwd;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    UserInfo userInfo;
    String uuid;

    @Override
    protected TradePwdPresenter createPresenter() {
        return new TradePwdPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_trade_pwd_email;
    }

    @Override
    public void initViewAndData() {
        userInfo = App.getInstance().getUserInfo();
        uuid=userInfo.getUserAccountUuid();
        tvEmail.setText(userInfo.getEmailAddress());
    }


    @OnClick({R.id.tv_get_vertify_code, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_vertify_code:
                String email=userInfo.getEmailAddress();
                if (TextUtils.isEmpty(email)) {
                    showShortToast(getResources().getString(R.string.pls_input_email));
                    return;
                }else if (!CommonUtil.isEmail(email)) {
                    showShortToast(getResources().getString(R.string.email_format_error));
                    return;
                }
                mvpPresenter.userSendEmail(
                        userInfo.getEmailAddress(),
                        uuid
                );
                break;
            case R.id.tv_sure:
                if (!doCheck()) {
                    return;
                }
                showLoadingDialog(null);
                mvpPresenter.userSafeResetByEmail(
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
        if (!CommonUtil.isEmail(userInfo.getEmailAddress())) {
            showShortToast(getResources().getString(R.string.email_format_error));
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
