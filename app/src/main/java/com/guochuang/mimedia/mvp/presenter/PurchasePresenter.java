package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.PayConfig;
import com.guochuang.mimedia.mvp.view.PurchaseView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class PurchasePresenter extends BasePresenter<PurchaseView> {
    public PurchasePresenter(PurchaseView view) {
        attachView(view);
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
    public void buyCityOwner(long regionId,double money,int payType,String channelCode,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().buyCityOwner(regionId,money,payType,channelCode,safetyCode)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setBuyCity(data);
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
    public void upgradeAgent(String channelCode,int payType,double originalMoney,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().upgradeAgent(channelCode,payType,originalMoney,safetyCode)), new ApiCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                mvpView.setUpgradeAgent(data);

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
    public void appCreateOrder(String channelCode,int payNumber,int payType,String longitude,String latitude,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().appCreateOrder(channelCode,payNumber,payType,longitude,latitude,safetyCode)), new ApiCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                mvpView.setBuyHonyComb(data);

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
    public void createSnatchOrder(String channelCode,int payType,long snatchId,int buyCount,String longitude,String latitude,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().createSnatchOrder(channelCode,payType,snatchId,buyCount,longitude,latitude,safetyCode)), new ApiCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                mvpView.setSnatch(data);

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
    public void buyNestAd(String channelCode,int payType,long nestTimeInfoId,int price,int totalPrice,String longitude,String latitude,String safetyCode){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().buyNestTime(channelCode,payType,nestTimeInfoId,price,totalPrice,longitude,latitude,safetyCode)), new ApiCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                mvpView.setBuyNestAd(data);

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
    public void calValue(double numericalValue,int type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().calValue(numericalValue,type)), new ApiCallback<CalValue>() {
            @Override
            public void onSuccess(CalValue data) {
                mvpView.setPayResult(data);

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
