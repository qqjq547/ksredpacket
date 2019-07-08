package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.http.exception.ApiException;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.mvp.view.AdListView;
import com.guochuang.mimedia.tools.RxUtil;

public class AdListPresenter extends BasePresenter<AdListView> {

    public AdListPresenter(AdListView view) {
        attachView(view);
    }

    public void getMyAdList(Integer status,int currentPage,int pageSize){
        addSubscription(RxUtil.createHttpObservable(ApiClient.getInstance().getApiStores().
                nestMyList(status,currentPage,pageSize)), new ApiCallback<Page<MyAd>>() {
            @Override
            public void onSuccess(Page<MyAd> data) {
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
