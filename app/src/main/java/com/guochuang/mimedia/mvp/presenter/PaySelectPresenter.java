package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.PayConfig;
import com.guochuang.mimedia.mvp.view.PaySelectView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class PaySelectPresenter extends BasePresenter<PaySelectView> {
    public PaySelectPresenter(PaySelectView view) {
        attachView(view);
    }
    public void calValue(double numericalValue,int type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().calValue(numericalValue,type)), new ApiCallback<CalValue>() {
            @Override
            public void onSuccess(CalValue data) {
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
    public void getPayType(String bizType){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getPayType(bizType)), new ApiCallback<PayConfig>() {
            @Override
            public void onSuccess(PayConfig data) {
                mvpView.setConfig(data);
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
