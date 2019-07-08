package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.view.IdentifyView;
import com.guochuang.mimedia.tools.RxUtil;

public class IdentifyPresenter extends BasePresenter<IdentifyView> {
    public IdentifyPresenter(IdentifyView view) {
        attachView(view);
    }



    public void getRegisterImageVerify(String mobile) {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                userPhoneCaptcha(mobile)), new ApiCallback<Captcha>() {
            @Override
            public void onSuccess(Captcha data) {
                mvpView.setVerifyData(data);

            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 实名认证
     * @param realName
     * @param idCard
     * @param uuid
     * @param captchastr
     */
    public void realName(String realName, String idCard, String uuid, String captchastr) {

        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                realName(realName,idCard,uuid,captchastr)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setData(data);
            }

            @Override
            public void onFailure(ApiException exception) {
                mvpView.setVerifyError(exception.getMessage());

            }

            @Override
            public void onFinish() {

            }
        });


    }
}
