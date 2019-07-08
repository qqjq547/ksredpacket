package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.PayeeUser;
import com.guochuang.mimedia.mvp.view.MyCaptureView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyCapturePresenter extends BasePresenter<MyCaptureView> {

    public MyCapturePresenter(MyCaptureView view) {
        attachView(view);
    }

    public void queryUserInfoByAccountUuid(String userAccountUuid){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                queryUserInfoByAccountUuid(userAccountUuid)), new ApiCallback<PayeeUser>() {
            @Override
            public void onSuccess(PayeeUser data) {
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
