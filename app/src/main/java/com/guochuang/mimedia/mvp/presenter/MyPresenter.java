package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RegionCore;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.view.MyView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyPresenter extends BasePresenter<MyView> {
    public MyPresenter(MyView view) {
        attachView(view);
    }
    public void getRecommendData(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRecommendData()), new ApiCallback<RecommendData>() {
            @Override
            public void onSuccess(RecommendData data) {
                mvpView.setRecommendData(data);

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
    public void getRegionCore(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRegionCore()), new ApiCallback<RegionCore>() {
            @Override
            public void onSuccess(RegionCore data) {
                mvpView.setRegionCoreData(data);

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
