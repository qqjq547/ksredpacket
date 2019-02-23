package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.model.NestFavorite;
import com.guochuang.mimedia.mvp.view.CollectNestAdView;
import com.guochuang.mimedia.mvp.view.InfoListView;
import com.guochuang.mimedia.tools.RxUtil;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public class CollectNestAdPresenter extends BasePresenter<CollectNestAdView> {
    public CollectNestAdPresenter(CollectNestAdView view) {
        attachView(view);
    }
    public void getNestAdFavorite(int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().getNestAdFavorite(currentPage,pageSize)), new ApiCallback<Page<NestFavorite>>() {
            @Override
            public void onSuccess(Page<NestFavorite> data) {
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