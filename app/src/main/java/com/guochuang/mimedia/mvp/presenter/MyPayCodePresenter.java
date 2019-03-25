package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.HttpResponse;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.PayCode;
import com.guochuang.mimedia.mvp.view.MyPayCodeView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class MyPayCodePresenter extends BasePresenter<MyPayCodeView> {

    public MyPayCodePresenter(MyPayCodeView view) {
        attachView(view);
    }

    public void queryQrcode(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                queryQrcode()), new ApiCallback<PayCode>() {
            @Override
            public void onSuccess(PayCode data) {
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
