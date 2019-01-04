package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AlipayAccout;
import com.guochuang.mimedia.mvp.view.MyAlipayView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyAlipayPresenter extends BasePresenter<MyAlipayView> {

    public MyAlipayPresenter(MyAlipayView view) {
        attachView(view);
    }

    public void userInfoBindAlipay(
            String alipayRealName,
            String alipayAccount,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userInfoBindAlipay(alipayRealName, alipayAccount, safetyCode)), new ApiCallback<String>() {
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


    public void userInfoAlipayAccout() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userInfoAlipayAccout()), new ApiCallback<AlipayAccout>() {
            @Override
            public void onSuccess(AlipayAccout data) {
                mvpView.setGetData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setGetError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }


}
