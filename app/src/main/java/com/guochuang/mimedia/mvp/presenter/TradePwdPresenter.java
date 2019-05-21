package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.view.TradePawView;
import com.guochuang.mimedia.tools.RxUtil;

public class TradePwdPresenter extends BasePresenter<TradePawView> {
    public TradePwdPresenter(TradePawView view) {
        attachView(view);
    }

    public void userSafeReset(
            String captcha,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSafeReset(captcha, safetyCode)), new ApiCallback<String>() {
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

    public void userSafeResetByEmail(
            String captcha,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSafeResetByEmail(captcha, safetyCode)), new ApiCallback<String>() {
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

    public void userSafeCodeImageVerify(
            String tenantCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userResetSafeCodeCaptcha(tenantCode)), new ApiCallback<Captcha>() {
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

    public void userSendSms(
            String mobile,
            String captcha,
            String uuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSmsResetSafetyCode(mobile, captcha, uuid)), new ApiCallback<String>() {
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
    public void userSendEmail(
            String email,
            String uuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userEmailResetSafetyCode(email, uuid)), new ApiCallback<String>() {
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
