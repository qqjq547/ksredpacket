package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.FengWoVideoOrLink;
import com.guochuang.mimedia.mvp.view.IntefaceWebView;
import com.guochuang.mimedia.tools.RxUtil;

public class WebPresenter extends BasePresenter<IntefaceWebView> {
    public WebPresenter(IntefaceWebView view) {
        attachView(view);

    }

    /**
     * get /beehiveBonus/randomQueryOne
     * <p>
     * 随机获取一条广告
     */
    public void getAdvertisement() {
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getAdvertisement()), new ApiCallback<FengWoVideoOrLink>() {
            @Override
            public void onSuccess(FengWoVideoOrLink data) {
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
