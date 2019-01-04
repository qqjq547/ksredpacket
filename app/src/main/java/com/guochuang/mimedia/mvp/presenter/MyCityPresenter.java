package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CurrentRegion;
import com.guochuang.mimedia.mvp.model.MyRegion;
import com.guochuang.mimedia.mvp.model.RegionDetail;
import com.guochuang.mimedia.mvp.view.MyCityView;
import com.guochuang.mimedia.tools.RxUtil;

public class MyCityPresenter extends BasePresenter<MyCityView> {
    public MyCityPresenter(MyCityView view) {
        attachView(view);
    }
    public void getMyRegion(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMyRegion()), new ApiCallback<MyRegion>() {
            @Override
            public void onSuccess(MyRegion data) {
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
    public void cityIsLock(long regionId,int isLock){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().cityIsLock(regionId,isLock)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setIsLock(data);

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
