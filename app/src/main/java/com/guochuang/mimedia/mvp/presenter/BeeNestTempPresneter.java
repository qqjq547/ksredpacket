package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.NestTemplate;
import com.guochuang.mimedia.mvp.view.BeeNestTempView;
import com.guochuang.mimedia.tools.RxUtil;

import java.util.List;

public class BeeNestTempPresneter extends BasePresenter<BeeNestTempView> {

    public BeeNestTempPresneter(BeeNestTempView view) {
        attachView(view);
    }

    public void getTempList(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                nesteTemplateList()), new ApiCallback<List<NestTemplate>>() {
            @Override
            public void onSuccess(List<NestTemplate> data) {
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
    public void deleteTemplate(long nestTemplateId){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                deleteNestTemplate(nestTemplateId)), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mvpView.setDelete(data);
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
