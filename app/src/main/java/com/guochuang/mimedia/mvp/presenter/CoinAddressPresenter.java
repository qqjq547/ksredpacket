package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.CoinAddress;
import com.guochuang.mimedia.mvp.view.CoinAddressView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class CoinAddressPresenter extends BasePresenter<CoinAddressView> {
    public CoinAddressPresenter(CoinAddressView view) {
        attachView(view);
    }

    public void getCoinAddress() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getCoinAddress()), new ApiCallback<List<CoinAddress>>() {
            @Override
            public void onSuccess(List<CoinAddress> data) {
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
