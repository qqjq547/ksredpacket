package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.view.RecommendView;
import com.guochuang.mimedia.tools.RxUtil;

public class RecommendPresenter extends BasePresenter<RecommendView> {

    public RecommendPresenter(RecommendView view) {
        attachView(view);
    }

    public void getRecommend(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                getRecommendData()), new ApiCallback<RecommendData>() {
            @Override
            public void onSuccess(RecommendData data) {
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
