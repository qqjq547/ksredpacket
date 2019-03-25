package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.PayeeUser;
import com.guochuang.mimedia.mvp.model.PaymentResult;
import com.guochuang.mimedia.mvp.view.KsbPayView;
import com.guochuang.mimedia.tools.RxUtil;

public class KsbPayPresenter extends BasePresenter<KsbPayView> {

    public KsbPayPresenter(KsbPayView view) {
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
    public void payMoney(String channelCode,String payerUserUuid,String payeeUserUuid,String coin,String safetyCode,String remark){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                payMoney(channelCode,payerUserUuid,payeeUserUuid,coin,safetyCode,remark)), new ApiCallback<PaymentResult>() {
            @Override
            public void onSuccess(PaymentResult data) {
                mvpView.setPayResult(data);
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
