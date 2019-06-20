package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.view.ForgetView;
import com.guochuang.mimedia.tools.RxUtil;

public class ResetPresenter extends BasePresenter<ForgetView> {
    public ResetPresenter(ForgetView view) {
        attachView(view);
    }

    public void getReset(
            String nationCode,
            String mobile,
            String captcha,
            String password
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userResetPassword(nationCode, mobile, captcha, password)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void getEmailReset(
            String email,
            String captcha,
            String password
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userEmailResetPassword(email,captcha, password)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getForgetImageVerify(
            String mobile
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userResetPasswordCaptcha(mobile)), new ApiCallback<Captcha>() {
            @Override
            public void onSuccess(Captcha data) {
                mvpView.setVerifyData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getForgetSmsVerify(
            String mobile,
            String captcha,
            String uuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSmsResetPassword(mobile, captcha, uuid)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setSmsData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setSmsError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void getForgetEmailVerifyRestPwd(
            String emailAddress
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userEmailReset_Password(emailAddress)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setSmsData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setSmsError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

}