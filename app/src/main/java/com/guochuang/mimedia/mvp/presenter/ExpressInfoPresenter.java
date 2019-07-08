package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.SnatchAddress;
import com.guochuang.mimedia.mvp.view.ExpressInfoView;
import com.guochuang.mimedia.tools.RxUtil;

public class ExpressInfoPresenter extends BasePresenter<ExpressInfoView> {
    public ExpressInfoPresenter(ExpressInfoView view) {
        attachView(view);
    }

    public void getSnatchDetail(long snatchId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getSnatchDetail(snatchId)), new ApiCallback<SnatchAddress>() {
            @Override
            public void onSuccess(SnatchAddress data) {
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
