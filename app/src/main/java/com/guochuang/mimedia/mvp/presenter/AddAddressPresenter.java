package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.AddAddressView;
import com.guochuang.mimedia.tools.RxUtil;

public class AddAddressPresenter extends BasePresenter<AddAddressView> {
    public AddAddressPresenter(AddAddressView view) {
        attachView(view);
    }

    public void addAddress(String trackName,String trackMobile, String province,String city,String district,String address,int isDefault) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAddressAdd(trackName,trackMobile,province,city,district,address,isDefault)), new ApiCallback<Boolean>() {
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
    public void delAddress(String userAddressUuid) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAddressDel(userAddressUuid)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setDelData(data);

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
    public void updateAddress(String userAddressUuid,String trackName,String trackMobile, String province,String city,String district,String address) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userAddressUpdate(userAddressUuid,trackName,trackMobile,province,city,district,address)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setUpdateData(data);

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
