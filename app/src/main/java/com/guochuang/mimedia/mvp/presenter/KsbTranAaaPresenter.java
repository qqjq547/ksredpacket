package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.view.AdBidView;
import com.guochuang.mimedia.mvp.view.KsbTranAaaView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class KsbTranAaaPresenter extends BasePresenter<KsbTranAaaView> {

    public KsbTranAaaPresenter(KsbTranAaaView view) {
        attachView(view);
    }

    public void getExchangeConfig(String digitalCurrency){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getExchangeConfig(digitalCurrency)), new ApiCallback<ExchangeConfig>() {
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

    public void intCal(String digitalCurrency,int type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                intCal(digitalCurrency,type)), new ApiCallback<DigitalIntCal>() {
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
    public void exchange(String sourceDigitalCurrency,String targetDigitalCurrency,double coin,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                exchange(sourceDigitalCurrency,targetDigitalCurrency,coin,safetyCode)), new ApiCallback<String>() {
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
}
