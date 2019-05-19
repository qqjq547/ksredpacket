package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.SealRecord;
import com.guochuang.mimedia.mvp.view.MyKsbDetailsView;
import com.guochuang.mimedia.mvp.view.MySealDetailsView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class MySealDetailsPresenter extends BasePresenter<MySealDetailsView> {
    public MySealDetailsPresenter(MySealDetailsView view) {
        attachView(view);
    }
    public void getCoinRecord(String type,String coinType,String startIndex,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getCoinRecord(type,coinType,startIndex,pageSize)), new ApiCallback<List<SealRecord>>() {
            @Override
            public void onSuccess(List<SealRecord> data) {
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
    public void getSubject(String type){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDictionaryType(type)), new ApiCallback<List<DictionaryType>>() {
            @Override
            public void onSuccess(List<DictionaryType> data) {
                mvpView.setSubject(data);

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
