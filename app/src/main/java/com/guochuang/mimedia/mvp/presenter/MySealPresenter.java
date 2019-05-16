package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AAARate;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.model.MySeal;
import com.guochuang.mimedia.mvp.view.MyAAAAView;
import com.guochuang.mimedia.mvp.view.MySealView;
import com.guochuang.mimedia.tools.RxUtil;

public class MySealPresenter extends BasePresenter<MySealView> {
    public MySealPresenter(MySealView view) {
        attachView(view);
    }

    public void getMySeal() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyKsb()), new ApiCallback<MySeal>() {
            @Override
            public void onSuccess(MySeal data) {
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

    /**
     * 获取AAA 的税率
     */
    public void getMyAAARate(String digitalCurrency) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyAAARate(digitalCurrency)), new ApiCallback<AAARate>() {
            @Override
            public void onSuccess(AAARate data) {
                mvpView.setAAARate(data);
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

