package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.mvp.view.AdInfoView;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.RxUtil;

public class AdInfoPresenter extends BasePresenter<AdInfoView> {

    public AdInfoPresenter(AdInfoView view) {
        attachView(view);
    }

    public void commonAdGetVedorAd(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                commonAdGetVedorAd()), new ApiCallback<AdInfo>() {
            @Override
            public void onSuccess(AdInfo data) {
                mvpView.setData(GsonUtil.GsonString(data));
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
