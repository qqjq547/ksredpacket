package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.SafeCenter;
import com.guochuang.mimedia.mvp.view.SafeCenterView;
import com.guochuang.mimedia.tools.RxUtil;

public class SafeCenterPresenter extends BasePresenter<SafeCenterView> {

    public SafeCenterPresenter(SafeCenterView view) {
        attachView(view);
    }

    public void userInfoSecurityCenter() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userInfoSecurityCenter()), new ApiCallback<SafeCenter>() {
            @Override
            public void onSuccess(SafeCenter data) {
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

    public void userAppWechatBind(
            String tenantCode,
            String systemCode,
            String code
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAppWechatBind(tenantCode, systemCode, code)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setBindWxData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setBindWxError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
