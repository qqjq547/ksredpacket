package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.view.BindingPhoneView;
import com.guochuang.mimedia.tools.RxUtil;

public class BindingPhonePresenter extends BasePresenter<BindingPhoneView> {
    public BindingPhonePresenter(BindingPhoneView view) {
        attachView(view);
    }

    public void getEmailVerify() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getBindMobileEmailVerify()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setEmailCaptchaData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setEmailCaptchaError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
    public void userSafeBindPhone(
            String mobile,
            String captcha,
            String emailCaptcha,
            String userAccountUuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSafeBindPhone(mobile, captcha,emailCaptcha, userAccountUuid)), new ApiCallback<BindingPhone>() {
            @Override
            public void onSuccess(BindingPhone data) {
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

    public void getMobileVertify(
            String mobile,
            String captcha,
            String uuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSmsBindMobile(mobile, captcha, uuid)), new ApiCallback<String>() {
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
