package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.view.SealTransferView;
import com.guochuang.mimedia.tools.RxUtil;

public class SealTransferPresenter extends BasePresenter<SealTransferView> {

    public SealTransferPresenter(SealTransferView view) {
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
    public void withdrawCoin(String digitalCurrency,String address,
                             double coin,String safetyCode,String mobile,String captcha,String safeType,String emailAddress){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                withdrawCoin(digitalCurrency,address,coin,safetyCode, mobile, captcha,safeType,emailAddress)), new ApiCallback<Boolean>() {
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
    public void sendSmsCode(String mobile,String uuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userSmsWithdrawCoin(mobile,uuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setSmsCode(data);
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
    public void sendEmailCode(
            String email,
            String uuid
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userEmailResetSafetyCode(email, uuid)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setEmailCode(data);

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
