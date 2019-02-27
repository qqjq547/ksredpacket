package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestTimeInfo;
import com.guochuang.mimedia.mvp.view.BidBrandView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class BidBrandPresenter extends BasePresenter<BidBrandView> {

    public BidBrandPresenter(BidBrandView view) {
        attachView(view);
    }

    public void setNestTimeInfo(long nestLocationId,String locationLatitude,String locationLongitude){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getNestTimeInfo(nestLocationId,locationLatitude,locationLongitude)), new ApiCallback<NestTimeInfo>() {
            @Override
            public void onSuccess(NestTimeInfo data) {
                mvpView.setTimeInfo(data);
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
