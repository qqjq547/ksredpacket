package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.view.MyAddressView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyAddressPresenter extends BasePresenter<MyAddressView> {
    public MyAddressPresenter(MyAddressView view) {
        attachView(view);
    }

    public void userAddressList(int currentPage,int pageSize) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAddressList(currentPage,pageSize)), new ApiCallback<Page<Address>>() {
            @Override
            public void onSuccess(Page<Address> data) {
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
    public void setWinAddress(long snatchId,String userAddressUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                setWinAddress(snatchId,userAddressUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setAddressData(data);
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
