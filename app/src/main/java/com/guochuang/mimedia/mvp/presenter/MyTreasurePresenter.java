package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.view.MyAddressView;
import com.guochuang.mimedia.mvp.view.MyTreasureView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyTreasurePresenter extends BasePresenter<MyTreasureView> {
    public MyTreasurePresenter(MyTreasureView view) {
        attachView(view);
    }

    public void getRecordList(int currentPage,int pageSize) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getSnatchRecordlist(currentPage,pageSize)), new ApiCallback<Page<Snatch>>() {
            @Override
            public void onSuccess(Page<Snatch> data) {
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
    public void getOrderVendor(long orderId) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getOrderVendor(orderId)), new ApiCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                mvpView.setVendor(data);
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
