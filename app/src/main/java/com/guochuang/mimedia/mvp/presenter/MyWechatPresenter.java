package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyWechat;
import com.guochuang.mimedia.mvp.view.MyView;
import com.guochuang.mimedia.mvp.view.MyWechatView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyWechatPresenter extends BasePresenter<MyWechatView> {
    public MyWechatPresenter(MyWechatView view) {
        attachView(view);
    }
    public void getWechatInfo(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getWechatInfo()), new ApiCallback<MyWechat>() {
            @Override
            public void onSuccess(MyWechat data) {
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


    public void userAppWechatBind(String mobile, String password, String systemCode, String wxCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().userAppWechatBind(mobile,password,systemCode,wxCode)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setBindSuccessAndLoginData(data);
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
    public void mobileBindWechat(String mobile, String password, String systemCode, String wxCode) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().mobileBindWechat(mobile,password,systemCode,wxCode)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setBindSuccessAndLoginData(data);
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
