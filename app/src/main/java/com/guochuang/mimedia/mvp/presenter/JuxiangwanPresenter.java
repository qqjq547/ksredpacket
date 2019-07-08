package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.JuxiangwanView;
import com.guochuang.mimedia.tools.RxUtil;

public class JuxiangwanPresenter extends BasePresenter<JuxiangwanView> {

    public JuxiangwanPresenter(JuxiangwanView view) {
        attachView(view);
    }

    public void addStatistics(String utoken, String deviceCode, String deviceFrom, String jumpUrl){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                addStatistics(utoken,deviceCode,deviceFrom,jumpUrl)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
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
}
