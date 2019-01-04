package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.view.MyKsbGrantView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyKsbGrantPresenter extends BasePresenter<MyKsbGrantView> {
    public MyKsbGrantPresenter(MyKsbGrantView view) {
        attachView(view);
    }

    public void userTransferAdd(
            String doneeAddress,
            double coin,
            String type,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userTransferAdd(doneeAddress, coin, type, safetyCode)), new ApiCallback<String>() {
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

    public void getMyKsb(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyKsb()), new ApiCallback<MyKsb>() {
            @Override
            public void onSuccess(MyKsb data) {
                mvpView.setKsbPreiceData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setKsbPreiceError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
