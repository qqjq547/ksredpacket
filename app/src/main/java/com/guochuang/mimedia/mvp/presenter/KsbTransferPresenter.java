package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.view.KsbTransferView;
import com.guochuang.mimedia.tools.RxUtil;

public class KsbTransferPresenter extends BasePresenter<KsbTransferView> {

    public KsbTransferPresenter(KsbTransferView view) {
        attachView(view);
    }

    public void userWithdrawals(
            int money,
            int payType,
            String id,
            String account,
            String safetyCode
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userWithdrawals(money, payType, id, account, safetyCode)), new ApiCallback<String>() {
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

    public void getMyKsb() {
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

    public void getMyKsbLimit() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userWithdrawalsLimit()), new ApiCallback<KsbTransfer>() {
            @Override
            public void onSuccess(KsbTransfer data) {
                mvpView.setConvertData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setConvertError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
