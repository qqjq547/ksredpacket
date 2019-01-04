package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.QrCode;
import com.guochuang.mimedia.mvp.view.ShareView;
import com.guochuang.mimedia.tools.RxUtil;

public class SharePresenter extends BasePresenter<ShareView> {
    public SharePresenter(ShareView view) {
        attachView(view);
    }

    public void getQrCode() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getQrCode()), new ApiCallback<QrCode>() {
            @Override
            public void onSuccess(QrCode data) {
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
