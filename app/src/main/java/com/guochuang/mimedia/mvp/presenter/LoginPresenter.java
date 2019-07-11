package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.view.LoginView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.RxUtil;

public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView view) {
        attachView(view);
    }

    public void getLogin(String username,
                         String password,
                         String captcha,
                         String systemCode,
                         Integer loginType,
                         String networkType,
                         String udid,
                         String deviceId,
                         String deviceName,
                         String deviceModel,
                         String vendor,
                         String operatingSystem,
                         String appVersion,
                         String deviceResolution,
                         String imei,
                         String pushId,
                         String uniqueFlag) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().userAccoutLogin(
                username,
                password,
                captcha,
                systemCode,
                loginType,
                networkType,
                udid,
                deviceId,
                deviceName,
                deviceModel,
                vendor,
                operatingSystem,
                appVersion,
                deviceResolution,
                imei,
                pushId,
                uniqueFlag
        )), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setLoginData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                //2719
                if(exception.getStatusCode() == Constant.GOTOAPLAYYWEIXIN) {
                    mvpView.goToAplayWeixin();
                    return;
                }
                mvpView.setLoginError(exception.getMessage());
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void appWechatLogin(
            String tenantCode,
            String systemCode,
            String code,
            Integer loginType,
            String networkType,
            String udid,
            String deviceId,
            String deviceName,
            String deviceModel,
            String vendor,
            String operatingSystem,
            String appVersion,
            String deviceResolution,
            String imei,
            String pushId
    ) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                appWechatLogin(
                        tenantCode,
                        systemCode,
                        code,
                        loginType,
                        networkType,
                        udid,
                        deviceId,
                        deviceName,
                        deviceModel,
                        vendor,
                        operatingSystem,
                        appVersion,
                        deviceResolution,
                        imei,
                        pushId)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setWxLoginData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setLoginError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
