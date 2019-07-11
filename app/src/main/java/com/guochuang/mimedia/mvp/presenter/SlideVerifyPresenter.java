package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.mvp.model.SlideVerifyData;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.mvp.view.SlideVerifyView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

public class SlideVerifyPresenter extends BasePresenter<SlideVerifyView> {

    public SlideVerifyPresenter(SlideVerifyView view) {
        attachView(view);
    }

    public void getVerifyData(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getSlideCaptcha()), new ApiCallback<SlideVerifyData>() {
            @Override
            public void onSuccess(SlideVerifyData data) {
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
    public void verifySlideCaptcha(String uniqueFlag,String xPercent,String timeStamp){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                verifySlideCaptcha(uniqueFlag,xPercent,timeStamp)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
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
}
