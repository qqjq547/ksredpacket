package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.CityBidRecord;
import com.guochuang.mimedia.mvp.view.CityBidRecordView;
import com.guochuang.mimedia.tools.RxUtil;

public class CityBidRecordPresenter extends BasePresenter<CityBidRecordView> {
    public CityBidRecordPresenter(CityBidRecordView view) {
        attachView(view);
    }
    public void getBidRecord(int currentPage,int pageSize,long regionId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getBidRecord(currentPage,pageSize,regionId)), new ApiCallback<Page<CityBidRecord>>() {
            @Override
            public void onSuccess(Page<CityBidRecord> data) {
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
