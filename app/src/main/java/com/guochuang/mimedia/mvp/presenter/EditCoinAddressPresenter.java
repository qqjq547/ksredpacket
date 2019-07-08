package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.EditCoinAddressView;
import com.guochuang.mimedia.tools.RxUtil;

public class EditCoinAddressPresenter extends BasePresenter<EditCoinAddressView> {
    public EditCoinAddressPresenter(EditCoinAddressView view) {
        attachView(view);
    }

    public void addAddress(int chainType,String chainAddress,String alias,String remark) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                addCoinAddress(chainType,chainAddress,alias,remark)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setAddData(data);

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
    public void editAddress(long addressBookId,String chainAddress,String alias,String remark) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                editCoinAddress(addressBookId,chainAddress,alias,remark)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setEditData(data);

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
    public void delAddress(long addressBookId,String chainAddress) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                deleteCoinAddress(addressBookId,chainAddress)), new ApiCallback<Boolean>() {
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

}
