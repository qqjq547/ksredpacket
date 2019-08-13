package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MallRegionAll;
import com.guochuang.mimedia.mvp.model.MallRegionInfo;
import com.guochuang.mimedia.mvp.view.CityBuyPreView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class CityBuyPrePresenter extends BasePresenter<CityBuyPreView> {

    public CityBuyPrePresenter(CityBuyPreView view) {
        attachView(view);
    }

    public void getRegionAll(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mallRegionAll()), new ApiCallback<List<MallRegionAll>>() {
            @Override
            public void onSuccess(List<MallRegionAll> data) {
                mvpView.setRegionAll(data);
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
    public void getRegionInfo(String code,int level){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                mallRegionGet(code,level)), new ApiCallback<MallRegionInfo>() {
            @Override
            public void onSuccess(MallRegionInfo data) {
                mvpView.setRegionInfo(data);
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
