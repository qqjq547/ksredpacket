package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RegionDetail;
import com.guochuang.mimedia.mvp.view.CityDetailView;
import com.guochuang.mimedia.tools.RxUtil;

public class CityDetailPresenter extends BasePresenter<CityDetailView> {
    public CityDetailPresenter(CityDetailView view) {
        attachView(view);
    }
    public void getRegionDetail(long regionId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getRegionDetail(regionId)), new ApiCallback<RegionDetail>() {
            @Override
            public void onSuccess(RegionDetail data) {
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
