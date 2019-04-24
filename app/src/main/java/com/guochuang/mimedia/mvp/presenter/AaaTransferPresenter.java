package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.view.AaaTranKsbView;
import com.guochuang.mimedia.mvp.view.AaaTransferView;
import com.guochuang.mimedia.tools.RxUtil;

public class AaaTransferPresenter extends BasePresenter<AaaTransferView> {

    public AaaTransferPresenter(AaaTransferView view) {
        attachView(view);
    }

    public void getExchangeConfig(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getExchangeConfig()), new ApiCallback<ExchangeConfig>() {
            @Override
            public void onSuccess(ExchangeConfig data) {
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

    public void intCal(int type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                intCal(type)), new ApiCallback<DigitalIntCal>() {
            @Override
            public void onSuccess(DigitalIntCal data) {
                mvpView.setIntCal(data);
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
    public void withdrawCoin(String address,double coin,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                withdrawCoin(address,coin,safetyCode)), new ApiCallback<Boolean>() {
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
