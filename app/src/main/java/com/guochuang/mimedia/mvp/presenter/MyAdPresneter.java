package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestStatistics;
import com.guochuang.mimedia.mvp.view.MyAdView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyAdPresneter extends BasePresenter<MyAdView> {

    public MyAdPresneter(MyAdView view) {
        attachView(view);
    }

    public void getMyStatistics(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getNestStatistics()), new ApiCallback<NestStatistics>() {
            @Override
            public void onSuccess(NestStatistics data) {
                mvpView.setStatistics(data);
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
