package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.view.BindingPhoneView;
import com.guochuang.mimedia.tools.RxUtil;

public class BindingPhonePresenter extends BasePresenter<BindingPhoneView> {
    public BindingPhonePresenter(BindingPhoneView view) {
        attachView(view);
    }

    public void userBindPhone(
            String mobile,
            String captcha,
            String userAccountUuid,
            String userBindPhone
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBindPhone(mobile, captcha, userAccountUuid, userBindPhone)), new ApiCallback<BindingPhone>() {
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

    public void userSendSms(
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

    public void userBindMobileCaptcha(
            String mobile
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBindMobileCaptcha(mobile)), new ApiCallback<Captcha>() {
            @Override
            public void onSuccess(Captcha data) {
                mvpView.setCaptchaData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setCaptchaError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void captchaIsEnabled(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                captchaIsEnabled()), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setCaptchaIsEnabled(data);
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

    /**
     * 判断是否存在手机号
     * @param phone
     */
    public void mobileExisted(String phone) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mobileExisted(phone)), new ApiCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mvpView.mobileExisted(data);
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

}
