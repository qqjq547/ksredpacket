package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.model.RecommendDetail;
import com.guochuang.mimedia.mvp.view.RecommendDetailView;
import com.guochuang.mimedia.mvp.view.WelcomeView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class RecommendDetailPresenter extends BasePresenter<RecommendDetailView> {
    public RecommendDetailPresenter(RecommendDetailView view) {
        attachView(view);
    }
    public void getMarketCount(){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getMarketCount()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mvpView.setMarketCount(data);

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
    public void getDirectAgent(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDirectAgent(currentPage,pageSize)), new ApiCallback<Page<RecommedUser>>() {
            @Override
            public void onSuccess(Page<RecommedUser> data) {
                mvpView.setRecommendUser(data);

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
    public void getDirectFans(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getDirectFans(currentPage,pageSize)), new ApiCallback<Page<RecommedUser>>() {
            @Override
            public void onSuccess(Page<RecommedUser> data) {
                mvpView.setRecommendUser(data);

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
