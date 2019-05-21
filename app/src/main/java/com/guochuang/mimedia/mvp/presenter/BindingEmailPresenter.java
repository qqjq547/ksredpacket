package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Email;
import com.guochuang.mimedia.mvp.view.BindingEmailView;
import com.guochuang.mimedia.tools.RxUtil;

public class BindingEmailPresenter extends BasePresenter<BindingEmailView> {
    public  BindingEmailPresenter(BindingEmailView view) {
        attachView(view);

    }

    public void getEmailVerify(String emailStr) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getEmailVerify(emailStr)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setEmailVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void applyEmail(String emailStr_, String verifyCode, String uuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().applyEmail(emailStr_,verifyCode,uuid)), new ApiCallback<Email>() {
            @Override
            public void onSuccess(Email data) {
                mvpView.setApplySuccess(data);
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
