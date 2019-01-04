package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.CardAddView;
import com.guochuang.mimedia.tools.RxUtil;

public class CardAddPresenter extends BasePresenter<CardAddView> {
    public CardAddPresenter(CardAddView view) {
        attachView(view);
    }

    public void userCardAdd(
            String bankName,
            String mobile,
            String name,
            String cardNumber,
            String type,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBankCardBind(bankName, mobile, name, cardNumber, type, safetyCode)), new ApiCallback<Boolean>() {
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

    public void userCardUpdate(
            String bankName,
            String mobile,
            String name,
            String cardNumber,
            String type,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBankCardUpdate(bankName, mobile, name, cardNumber, type, safetyCode)), new ApiCallback<Boolean>() {
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

    public void userCardUnbind(
            String id,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userBankCardUnbind(id, safetyCode)), new ApiCallback<Boolean>() {
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
